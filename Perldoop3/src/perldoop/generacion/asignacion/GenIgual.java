package perldoop.generacion.asignacion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import perldoop.generacion.coleccion.GenColeccion;
import perldoop.generacion.util.Casting;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.SimboloAux;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.acceso.Acceso;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.expresion.ExpAcceso;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.arbol.variable.Variable;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.preprocesador.TagsInicializacion;
import perldoop.modelo.preprocesador.TagsTipo;
import perldoop.modelo.semantica.EntradaVariable;
import perldoop.modelo.semantica.Tipo;
import perldoop.generacion.util.ColIterator;
import perldoop.modelo.arbol.acceso.AccesoCol;
import perldoop.modelo.arbol.acceso.AccesoColRef;
import perldoop.modelo.generacion.Declaracion;
import perldoop.util.Buscar;
import perldoop.modelo.preprocesador.Tags;

/**
 * Clase para la semantica de igual
 *
 * @author César Pomar
 */
public class GenIgual {

    private TablaGenerador tabla;

    /**
     * Contruye el gener
     *
     * @param tabla Tabla de simbolos
     */
    public GenIgual(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Igual s) {
        Expresion izq = s.getIzquierda();
        Expresion der = s.getDerecha();
        if (izq.getValor() instanceof ColParentesis) {
            multiple(s);
        } else if (der instanceof ExpColeccion && ((ExpColeccion) der).getColeccion().getLista().getExpresiones().isEmpty()) {
            inicializacion(s);
        } else {
            simple(s);
        }
    }

    /**
     * Asignación simple, es decir la asignacion se realiza sobre una sola variable
     *
     * @param s Simbolo de asignacion
     */
    private void simple(Igual s) {
        Expresion der = s.getDerecha();
        Expresion izq = s.getIzquierda();
        Simbolo derAux = Casting.colToScalar(der, izq);
        derAux = scalarToCol(derAux, izq.getTipo());
        //Las variables de tipo colección se copian en su asignación, siempre que no haya un casting que lo haga
        if (derAux.getTipo().isColeccion() && Buscar.isVariable(der) && der.getTipo().equals(izq.getTipo())) {
            derAux.getCodigoGenerado().insert(0, "Pd.copy(").append(")");
        }
        StringBuilder codigo = asignacion(izq, s.getOperador().getComentario(), derAux);
        //Si es una referencia fruto de un acceso y todabia no es una sentencia, hay que crear la referencia real
        if (izq.getTipo().isRef() && Buscar.getExpresion(izq) instanceof ExpAcceso
                && !(Buscar.getUso((Expresion) s.getPadre()).getPadre() instanceof StcLista)) {
            StringBuilder ref = Tipos.declaracion(izq.getTipo()).append("(").insert(0, "new ");
            codigo.insert(0, ref).append(")");
        }
        s.setCodigoGenerado(codigo);
    }

    /**
     * Asignación multiple, es decir se asignan multiples variables
     *
     * @param s Simbolo de asignacion
     */
    private void multiple(Igual s) {
        Simbolo uso = Buscar.getUso((Expresion) s.getPadre());
        boolean sentencia = uso instanceof Lista && uso.getPadre() instanceof StcLista;
        List<Expresion> variables = Buscar.getExpresiones(s.getIzquierda());
        List<Simbolo> valores = (List) Buscar.getExpresiones(s.getDerecha());
        List<StringBuilder> atomicidad = new ArrayList<>(variables.size());
        List<Simbolo> asignaciones = new ArrayList<>(variables.size());
        List<StringBuilder> comentarios = new ArrayList<>(variables.size());
        //No hay ninguna coleccion a la derecha
        comentarios.add(new StringBuilder(s.getOperador().getComentario()));
        ColIterator varIt = new ColIterator((Coleccion) s.getIzquierda().getValor());
        //Comprobamos intercambios de variables
        atomicidad(s, atomicidad, variables, valores, sentencia);
        int i;
        String nValores = null;
        if (sentencia && (valores.size() > 1 || !valores.get(0).getTipo().isColeccion())) {
            ColIterator valIt = valores.size() > 1 ? new ColIterator((Coleccion) s.getDerecha().getValor()) : new ColIterator();
            //Variables que no serian inicializadas
            checkValores(s, variables, valores);
            //Variables con valores
            for (i = 0; varIt.hasNext() && valIt.hasNext(); i++) {
                comentarios.add(new StringBuilder(100).append(varIt.getComentario()).append(valIt.getComentario()));
                Simbolo var = variables.get(i);
                varIt.next();
                //Si no es coleccion coge su valor
                if (!var.getTipo().isColeccion()) {
                    valIt.next();
                    asignaciones.add(new SimboloAux(var.getTipo(), asignacion(var, "", checkRef(var, valores.get(i)))));
                } else {
                    //Si es coleccion coge todos los valores restantes
                    Simbolo aux = new SimboloAux(var.getTipo());
                    List<Expresion> subVals = new ArrayList<>(100);
                    List<Terminal> seps = new ArrayList<>(100);
                    while (valIt.hasNext()) {
                        subVals.add(valIt.next());
                        Terminal terminal = new Terminal();
                        terminal.setComentario(valIt.getComentario().toString());
                        seps.add(terminal);
                    }
                    aux.setCodigoGenerado(GenColeccion.genColeccion(subVals, seps, var.getTipo()));
                    asignaciones.add(new SimboloAux(var.getTipo(), asignacion(var, "", aux)));
                    valores.clear();
                    break;
                }
            }
            comentarios.add(new StringBuilder(valIt.getComentario()));
        } else {
            Simbolo exp = valores.get(0);
            Tipo t = exp.getTipo();
            String valor;
            if (!t.isColeccion()) {
                comentarios.add(new StringBuilder(varIt.getComentario()));
                Simbolo var = variables.get(0);
                varIt.next();
                if (var.getTipo().isColeccion()) {
                    asignaciones.add(new SimboloAux(var.getTipo(), asignacion(var, "", scalarToCol(exp, t))));
                } else {
                    asignaciones.add(new SimboloAux(var.getTipo(), asignacion(var, "", exp)));
                }
                i = 1;
                nValores = "1";
            } else {
                if (t.isMap() || !Buscar.isVariable(s.getDerecha())) {
                    //Los mapas deben secuencializarse en un array
                    if (t.isMap()) {
                        t = t.getSubtipo(1);
                        t.add(0, Tipo.ARRAY);
                    }
                    valor = tabla.getGestorReservas().getAux();
                    tabla.getDeclaraciones().add(new Declaracion(s, t, valor));
                    atomicidad.add(new StringBuilder(100).append(valor).append("=").append(Casting.casting(s.getDerecha(), t)));
                    nValores = valor + ".length";
                } else {
                    valor = exp.toString();
                    nValores = valor + (t.isArray() ? ".length" : ".size()");
                }
                String get1 = t.isArray() ? "[" : ".get(";
                String get2 = t.isArray() ? "]" : ")";
                StringBuilder cc = new StringBuilder(valor).append(get1);
                Simbolo coleccion = new SimboloAux(t.getSubtipo(1), cc);
                int len = cc.length();
                //Asginamos cada variable a una posicion
                for (i = 0; varIt.hasNext(); i++) {
                    cc.append(i).append(get2);
                    comentarios.add(new StringBuilder(varIt.getComentario()));
                    Simbolo var = variables.get(i);
                    varIt.next();
                    //Si encontramos una coleccion le asignamos el resto y cortamos
                    if (!var.getTipo().isColeccion()) {
                        asignaciones.add(new SimboloAux(var.getTipo(), asignacion(var, "", coleccion)));
                    } else {
                        coleccion = new SimboloAux(t, new StringBuilder(100).append("Pd.subEquals(").append(i).append(",").append(valor).append(")"));
                        asignaciones.add(new SimboloAux(var.getTipo(), asignacion(var, "", coleccion)));
                        break;
                    }
                    cc.setLength(len);
                }
            }
        }
        checkValores(s, variables.subList(i, variables.size()), valores);
        Iterator<Simbolo> it = valores.iterator();
        //Asignamos los valores por defecto
        for (; varIt.hasNext(); i++) {
            comentarios.add(new StringBuilder(varIt.getComentario()));
            varIt.next();
            Simbolo var = variables.get(i);
            asignaciones.add(new SimboloAux(var.getTipo(), asignacion(var, "", checkRef(var, it.next()))));
        }
        comentarios.add(new StringBuilder(varIt.getComentario()));
        //Escritura de asignaciones
        multiasignacion(s, atomicidad, asignaciones, comentarios, nValores, sentencia);
    }

    /**
     * Asignación para inicializacion colección
     *
     * @param s Simbolo de asignacion
     */
    private void inicializacion(Igual s) {
        List<Token> sizes = null;
        Tags etiquetas = s.getOperador().getEtiquetas();
        int accesos = Buscar.accesos(s.getIzquierda());
        //Obtenemos las etiquetas de tamaño
        if (etiquetas == null) {
            sizes = new ArrayList<>();
        } else if (etiquetas instanceof TagsInicializacion) {
            sizes = ((TagsInicializacion) etiquetas).getSizes();
        } else if (etiquetas instanceof TagsTipo) {
            sizes = ((TagsTipo) etiquetas).getSizes();
            //Por cada acceso a la coleccion eliminamos un tamaño    
            if (accesos < sizes.size()) {
                sizes = sizes.subList(accesos, sizes.size());
            } else {
                sizes = new ArrayList<>();
            }
        }
        //Cogemos los tamaños y tranformanos los tamaños variables
        List<String> tams = new ArrayList<>(sizes.size());
        for (Token token : sizes) {
            if (token == null) {
                break;
            }
            String valor = token.getValor();
            valor = valor.substring(1, valor.length() - 1);
            char c = valor.charAt(0);
            if (c == '$' || c == '@' || c == '%') {
                EntradaVariable entrada = tabla.getTablaSimbolos().buscarVariable(valor.substring(1), c);
                valor = Casting.toInteger(new SimboloAux(entrada.getTipo(), new StringBuilder(entrada.getAlias()))).toString();
            }
            tams.add(valor);
        }
        //Generamos lainicializacion
        Tipo t = s.getTipo();
        if (t.isRef() && accesos > 0) {
            t = t.getSubtipo(1);
        }
        StringBuilder ini = new StringBuilder(100);
        if (t.isRef()) {
            ini.append(Tipos.inicializacion(t)).append("(");
            ini.append(Tipos.inicializacion(t.getSubtipo(1), tams.toArray(new String[tams.size()])));
            ini.append(")");
        } else {
            ini.append(Tipos.inicializacion(t, tams.toArray(new String[tams.size()])));
        }
        //Generamos un simbolo y lo asignamos
        Simbolo aux = new SimboloAux(new Tipo(s.getIzquierda().getTipo()), ini);
        aux.setPadre(s);
        s.setCodigoGenerado(asignacion(s.getIzquierda(), s.getOperador().getComentario(), aux));
    }

    /**
     * Realiza la asignacion con comentarios en los simbolos
     *
     * @param izq Simbolo izquierda
     * @param igual Comentario simbolo igual
     * @param der Simbolo derecho
     * @return Codigo asignacion
     */
    public static StringBuilder asignacion(Simbolo izq, String igual, Simbolo der) {
        StringBuilder codigo = null;
        //Usar una funcion en lugar del operador =
        if (izq instanceof ExpAcceso) {
            Acceso acceso = ((ExpAcceso) izq).getAcceso();
            Tipo colT = acceso.getTipo();
            if (acceso instanceof AccesoCol) {
                colT = ((AccesoCol) acceso).getColeccion().getTipo();
            } else if (acceso instanceof AccesoColRef) {
                colT = ((AccesoColRef) acceso).getColeccion().getTipo();
            }
            Tipo t = acceso.getExpresion().getTipo();
            Tipo st = t.getSubtipo(1);
            if (colT.isColeccion() || t.isList() || t.isMap() || (t.isRef() && (st.isList() || st.isMap()))) {
                codigo = new StringBuilder(100);
                codigo.append(izq.getCodigoGenerado())
                        .append(igual)
                        .append(Casting.casting(checkRef(izq, der), izq.getTipo())).append(")");
            }
        }
        //Usar operador = para la asignación
        if (codigo == null) {
            codigo = new StringBuilder(100);
            codigo.append(izq.getCodigoGenerado())
                    .append("=").append(igual)
                    .append(Casting.casting(checkRef(izq, der), izq.getTipo()));
        }
        return codigo;
    }

    /**
     * Une multiples asignaciones teniendo en cuenta el contexto de su uso
     *
     * @param s Simbolo asignacion
     * @param atomicidad Asignaciones para conservar la atomicidad
     * @param asignacion Asignaciones de la multiasignacion
     * @param comentarios Comentarios de la multiasignacion
     * @param valores Numero de valores, solo necesario si no es sentencia
     * @param stc Es sentencia
     */
    private void multiasignacion(Igual s, List<StringBuilder> atomicidad, List<Simbolo> asignacion, List<StringBuilder> comentarios, String valores, boolean stc) {
        StringBuilder codigo = new StringBuilder(1000);
        if (stc) {
            for (StringBuilder sb : atomicidad) {
                codigo.append(sb).append(";");
            }
            codigo.append(s.getOperador().getComentario());
            Iterator<StringBuilder> itc = comentarios.iterator();
            Iterator<Simbolo> ita = asignacion.iterator();
            codigo.append(itc.next());
            while (ita.hasNext()) {
                codigo.append(ita.next());
                if (ita.hasNext()) {
                    codigo.append(";");
                }
            }
            codigo.append(itc.next());
        } else {
            codigo.append("Pd.equals(").append(s.getOperador().getComentario());
            if (!atomicidad.isEmpty()) {
                codigo.append("new Object[]{");
                for (StringBuilder sb : atomicidad) {
                    codigo.append(sb).append(",");
                }
                codigo.append("},");
            }
            codigo.append(valores).append(",");
            codigo.append(comentarios.get(0));
            List<Terminal> separadores = new ArrayList<>(comentarios.size());
            for (StringBuilder c : comentarios) {
                Terminal ter = new Terminal();
                ter.setCodigoGenerado(c);
                separadores.add(ter);
            }
            codigo.append(GenColeccion.genColeccion(asignacion, separadores, new Tipo(Tipo.ARRAY, Tipo.BOX)));
            codigo.append(")");
        }
        s.setCodigoGenerado(codigo);
    }

    /**
     * Asegura que todas las variables sean asignadas y que no haya ninguna sin inicializar en la asignacion
     *
     * @param s Igual
     * @param variables Variables
     * @param valores Valores
     */
    private void checkValores(Igual s, List<Expresion> variables, List<Simbolo> valores) {
        if (variables.size() > valores.size()) {
            for (int i = valores.size(); i < variables.size(); i++) {
                Expresion exp = variables.get(i);
                valores.add(new SimboloAux(exp.getTipo(), new StringBuilder("null")));

            }
        }
    }

    /**
     * Combierte un escalar a unca coleccion
     *
     * @param escalar Escalar
     * @param col Coleccion
     * @return Escalar como coleccion
     */
    private Simbolo scalarToCol(Simbolo escalar, Tipo col) {
        if (!escalar.getTipo().isColeccion() && col.isColeccion()) {
            Tipo t = col.getSubtipo(1).add(0, Tipo.ARRAY);
            StringBuilder codigo = new StringBuilder(100);
            codigo.append(Tipos.inicializacion(t)).append("{").append(Casting.casting(escalar, t.getSubtipo(1))).append("}");
            Simbolo aux = new SimboloAux(t, codigo);
            aux.setCodigoGenerado(Casting.casting(aux, t));
            return aux;
        }
        return escalar;
    }

    /**
     * Gestiona la optimicacion de creacion de acceso a referencia en asignaciones
     *
     * @param izq Simbolo izquierda
     * @param der Simbolo derecha
     * @return Simbolo derecha adaptado
     */
    private static Simbolo checkRef(Simbolo izq, Simbolo der) {
        if (izq instanceof Expresion && der instanceof Expresion) {
            return checkRef((Expresion) izq, (Expresion) der);
        }
        return der;
    }

    /**
     * Gestiona la optimicacion de creacion de acceso a referencia en asignaciones
     *
     * @param izq Expresion izquierda
     * @param der Expresion derecha
     * @return Expresion derecha adaptada
     */
    private static Simbolo checkRef(Expresion izq, Expresion der) {
        if (!der.getTipo().isRef() && !der.getTipo().isBox()) {
            return der;
        }
        izq = Buscar.getExpresion(izq);
        der = Buscar.getExpresion(der);
        boolean izqA = !izq.getTipo().isBox() && (izq.getValor() instanceof AccesoCol || izq.getValor() instanceof AccesoColRef);
        boolean derA = !der.getTipo().isBox() && (der.getValor() instanceof AccesoCol || der.getValor() instanceof AccesoColRef);
        if (izqA && !derA) {
            Simbolo aux = new SimboloAux(der);
            //El casting debe ir antes
            if (der.getTipo().isBox()) {
                aux.setCodigoGenerado(Casting.toRef(aux, izq.getTipo()));
                aux.setTipo(izq.getTipo());
            }
            aux.getCodigoGenerado().append(".get()");
            return aux;
        }
        if (!izqA && derA) {
            StringBuilder codigo = new StringBuilder(100).append("new ").append(Tipos.declaracion(der.getTipo()));
            codigo.append("(").append(der).append(")");
            return new SimboloAux(der.getTipo(), codigo);
        }
        return der;

    }

    /**
     * Asegura la atomicidad entre las asignaciones
     *
     * @param s Simbolo igual
     * @param asignaciones Asignaciones
     * @param variables Variables
     * @param valores Valores
     * @param stc Es sentencia
     */
    public void atomicidad(Igual s, List<StringBuilder> asignaciones, List<Expresion> variables, List<Simbolo> valores, boolean stc) {
        HashSet<String> escrituras = new HashSet<>(variables.size());
        List<Integer> noAtomica = new ArrayList<>(variables.size());
        //Cargamos el nombre de todas las variables
        for (Expresion exp : variables) {
            Variable var = Buscar.buscarVariable(exp);
            String alias = tabla.getTablaSimbolos().buscarVariable(var.getVar().getValor(), Buscar.getContexto(var)).getAlias();
            escrituras.add(alias);
        }
        EXT:
        //Buscamos las coincidencias en el lado derecho
        for (int i = 0; i < valores.size(); i++) {
            Simbolo valor = valores.get(i);
            //Accesos de 2 nivel nunca sabes si son atomicos
            List<ExpAcceso> accesos = Buscar.buscarClases(valor, ExpAcceso.class);
            for (ExpAcceso acceso : accesos) {
                if (Buscar.accesos(acceso) > 1 && Buscar.isVariable(acceso)) {
                    noAtomica.add(i);
                    continue EXT;
                }
            }
            //La misma variable en lectura y escritura
            List<Variable> vars = Buscar.buscarClases(valor, Variable.class);
            for (Variable var : vars) {
                String alias = tabla.getTablaSimbolos().buscarVariable(var.getVar().getValor(), Buscar.getContexto(var)).getAlias();
                if (escrituras.contains(alias)) {
                    noAtomica.add(i);
                    continue EXT;
                }
            }
        }
        //Si se encontro una poisble lectura y escritura de la misma variable
        if (!noAtomica.isEmpty()) {
            StringBuilder array = new StringBuilder(100);
            //Variable ausiliar para guardar valores
            String var = tabla.getGestorReservas().getAux();
            if (stc) {
                array.append("Object[] ");
            } else {
                tabla.getDeclaraciones().add(new Declaracion(s, (Tipo) null, "Object[] " + var));
            }
            //Substituimos
            asignaciones.add(array);
            array.append(var).append(" = new Object[]{");
            int p = 0;
            Iterator<Integer> it = noAtomica.iterator();
            while (it.hasNext()) {
                int i = it.next();
                Simbolo exp = valores.get(i);
                //Crear referencias
                if (exp.getTipo().isRef() && exp instanceof Expresion && (((Expresion) exp).getValor() instanceof AccesoCol
                        || ((Expresion) exp).getValor() instanceof AccesoColRef)) {
                    array.append("new ").append(Tipos.declaracion(exp.getTipo())).append("(").append(exp).append(")");
                } else {
                    array.append(exp);
                }
                if (it.hasNext()) {
                    array.append(",");
                }
                //Substituto
                Simbolo aux = new SimboloAux(exp.getTipo());
                StringBuilder nuevaExp = new StringBuilder(50);
                nuevaExp.append("((").append(Tipos.declaracion(aux.getTipo())).append(")");
                nuevaExp.append(var).append("[").append(p++).append("])");
                aux.setCodigoGenerado(nuevaExp);
                valores.set(i, aux);
            }
            array.append("}");
        }
    }

}
