package perldoop.generacion.asignacion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import perldoop.generacion.util.Casting;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.SimboloAux;
import perldoop.modelo.arbol.acceso.Acceso;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.expresion.ExpAcceso;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.ExpVariable;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.arbol.variable.VarMy;
import perldoop.modelo.arbol.variable.VarOur;
import perldoop.modelo.arbol.variable.Variable;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.preprocesador.Etiquetas;
import perldoop.modelo.preprocesador.EtiquetasInicializacion;
import perldoop.modelo.preprocesador.EtiquetasTipo;
import perldoop.modelo.semantica.EntradaVariable;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

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
        if (s.getIzquierda() instanceof ExpColeccion) {
            multiple(s);
        } else if (s.getDerecha() instanceof ExpColeccion) {
            ExpColeccion expcol = (ExpColeccion) s.getDerecha();
            if (expcol.getColeccion().getLista().getElementos().isEmpty()) {
                inicializacion(s);
            } else {
                simple(s);
            }
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
        s.setCodigoGenerado(asignacion(s.getIzquierda(), s.getIgual().getComentario(),s.getDerecha()));
    }

    /**
     * Asignación multiple, es decir se asignan multiples variables
     *
     * @param s Simbolo de asignacion
     */
    private void multiple(Igual s) {
        List<Expresion> variables = ((ColParentesis) ((ExpColeccion) s.getIzquierda()).getColeccion()).getLista().getExpresiones();
        List<StringBuilder> asignaciones = new ArrayList<>(variables.size());
        //Si la derecha es una coleccion y a la derecha hay un conjunto de expresiones o solo una entre parentesis
        if (s.getDerecha() instanceof ExpColeccion && (s.getDerecha().getTipo() == null || !s.getDerecha().getTipo().isColeccion())) {
            List<Simbolo> valores = (List) ((ColParentesis) ((ExpColeccion) s.getDerecha()).getColeccion()).getLista().getExpresiones();
            checkInicializacion(variables, valores);
            atomicidad(asignaciones, variables, valores);
            for (int i = 0; i < variables.size() && i < valores.size(); i++) {
                Simbolo valor = valores.get(i);
                asignaciones.add(asignacion(variables.get(i), "", valor));
            }
        } else {
            String valor;
            Tipo t = s.getDerecha().getTipo();
            if (t.isMap() || !Buscar.isVariable(s.getDerecha())) {
                //Los mapas deben secuencializarse en un array
                if (t.isMap()) {
                    t = t.getSubtipo(1);
                    t.add(0, Tipo.ARRAY);
                }
                StringBuilder variable = new StringBuilder(100);
                valor = tabla.getGestorReservas().getAux();
                variable.append(Tipos.declaracion(t)).append(" ").append(valor).append(";");
                asignaciones.add(new StringBuilder(100).append(valor).append("=").append(Casting.casting(s.getDerecha(), t)));
                tabla.getDeclaraciones().add(variable);
            } else {
                valor = s.getDerecha().getCodigoGenerado().toString();
            }
            Simbolo coleccion = new SimboloAux(t.getSubtipo(1));
            if (t.isArray()) {
                for (int i = 0; i < variables.size(); i++) {
                    coleccion.setCodigoGenerado(new StringBuilder(valor).append("[").append(i).append(']'));
                    asignaciones.add(asignacion(variables.get(i), "", coleccion));
                }
            } else {
                for (int i = 0; i < variables.size(); i++) {
                    coleccion.setCodigoGenerado(new StringBuilder(valor).append(".get(").append(i).append(')'));
                    asignaciones.add(asignacion(variables.get(i), "", coleccion));
                }
            }
        }
        s.setCodigoGenerado(multiasignacion(s, asignaciones));
    }

    /**
     * Asignación para inicializacion colección
     *
     * @param s Simbolo de asignacion
     */
    private void inicializacion(Igual s) {
        List<Token> sizes = null;
        Etiquetas etiquetas = s.getIgual().getEtiquetas();
        int accesos = Buscar.accesos(s.getIzquierda());
        //Obtenemos las etiquetas de tamaño
        if (etiquetas == null) {
            sizes = new ArrayList<>();
        } else if (etiquetas instanceof EtiquetasInicializacion) {
            sizes = ((EtiquetasInicializacion) etiquetas).getSizes();
        } else if (etiquetas instanceof EtiquetasTipo) {
            sizes = ((EtiquetasTipo) etiquetas).getSizes();
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
            if (c == '$' || c == '@') {
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
        s.setCodigoGenerado(asignacion(s.getIzquierda(), s.getIgual().getComentario(), aux));
    }

    /**
     * Realiza la asignacion
     *
     * @param izq Simbolo izquierda
     * @param igual Comentario simbolo igual
     * @param der Simbolo derecho
     * @return Codigo asignacion
     */
    public static StringBuilder asignacion(Simbolo izq, String igual, Simbolo der) {
        StringBuilder codigo = null;
        Simbolo derAux = Casting.ColtoScalar(der,izq);
        //si la derecha es un box y la izquierda una referencia, hacemos un pre casting
        if(izq.getTipo().isRef() && der.getTipo().isBox()){
            derAux.setCodigoGenerado(Casting.casting(derAux, izq.getTipo()));
            derAux.setTipo(izq.getTipo());
        }  
        //Si la izquierda es un acceso y la rerecha una referencia real, se hace el get
        if (derAux.getTipo().isRef() && izq instanceof ExpAcceso && !(der instanceof ExpAcceso || der instanceof SimboloAux)) {
            derAux.getCodigoGenerado().append(".get()");
        }
        //Las variables de tipo colección se copian en su asignación
        if (derAux.getTipo().isColeccion() && Buscar.isVariable(der)) {
            derAux.getCodigoGenerado().insert(0, "Pd.copy(").append(")");
        }
        //Usar una funcion en lugar del operador =
        if (izq instanceof ExpAcceso) {
            Acceso acceso = ((ExpAcceso) izq).getAcceso();
            Tipo t = acceso.getExpresion().getTipo();
            Tipo st = t.getSubtipo(1);
            if (acceso.getTipo().isColeccion() || t.isList() || t.isMap() || (t.isRef() && (st.isList() || st.isMap()))) {
                codigo = new StringBuilder(100);
                codigo.append(izq.getCodigoGenerado())
                        .append(igual)
                        .append(Casting.casting(derAux, izq.getTipo())).append(")");
            }
        }
        //Usar operador = para la asignación
        if (codigo == null) {
            codigo = new StringBuilder(100);
            codigo.append(izq.getCodigoGenerado())
                    .append("=").append(igual)
                    .append(Casting.casting(derAux, izq.getTipo()));
        }

        //Si es una referencia fruto de un acceso y todabia no es una sentencia, hay que crear la referencia real
        if (izq.getTipo().isRef() && izq instanceof ExpAcceso && !(Buscar.getPadre(der, 3) instanceof StcLista)) {
            StringBuilder ref = Tipos.declaracion(izq.getTipo()).append("(").insert(0, "new ");
            codigo.insert(0, ref).append(")");
        }
        return codigo;
    }

    /**
     * Une multiples asignaciones teniendo en cuenta el contexto de su uso
     *
     * @param s Simbolo asginacion
     * @param asignaciones Lista de asignaciones
     * @return Codigo asignaciones
     */
    private StringBuilder multiasignacion(Igual s, List<StringBuilder> asignaciones) {
        StringBuilder codigo = new StringBuilder(100);
        if (Buscar.getPadre(s, 2) instanceof StcLista) {
            Iterator<StringBuilder> it = asignaciones.iterator();
            while (it.hasNext()) {
                codigo.append(it.next());
                if (it.hasNext()) {
                    codigo.append(";");
                }
            }
        } else {
            Iterator<StringBuilder> itAsigns = asignaciones.iterator();
            codigo.append("Pd.equals(");
            while (itAsigns.hasNext()) {
                codigo.append(itAsigns.next());
                if (itAsigns.hasNext()) {
                    codigo.append(",");
                }
            }
            codigo.append(")");
        }
        return codigo;
    }

    /**
     * Asegura que no se colaran variables sin inicializar cuando hay mas variables que valores
     *
     * @param variables Variables
     * @param valores Valores
     */
    public void checkInicializacion(List<Expresion> variables, List<Simbolo> valores) {
        if (variables.size() > valores.size()) {
            for (int i = 0; i < variables.size(); i++) {
                Expresion exp = variables.get(i);
                if (exp instanceof ExpVariable) {
                    Variable var = ((ExpVariable) exp).getVariable();
                    if (var instanceof VarMy || var instanceof VarOur) {
                        valores.add(new SimboloAux(exp.getTipo(), new StringBuilder(Tipos.valoreDefecto(exp.getTipo()))));
                    } else {
                        //Si ya esta asignada y no tiene valor la ignoramos
                        variables.remove(i);
                        i--;
                    }
                }

            }
        }
    }

    /**
     * Asegura la atomicidad entre las asignaciones
     *
     * @param asignaciones Asignaciones
     * @param variables Variables
     * @param valores Valores
     */
    public void atomicidad(List<StringBuilder> asignaciones, List<Expresion> variables, List<Simbolo> valores) {
        HashSet<String> escrituras = new HashSet<>(variables.size());
        List<Integer> noAtomica = new ArrayList<>(variables.size());
        for (Expresion exp : variables) {
            Variable var = Buscar.buscarVariable(exp);
            String alias = tabla.getTablaSimbolos().buscarVariable(var.getVar().getValor(), Buscar.getContexto(var)).getAlias();
            escrituras.add(alias);
        }
        EXT:
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
        if (!noAtomica.isEmpty()) {
            //Variable ausiliar para guardar valores
            String var = tabla.getGestorReservas().getAux();
            StringBuilder dec = new StringBuilder(20);
            dec.append("Object[] ").append(var).append(";");
            tabla.getDeclaraciones().add(dec);
            //Substituimos
            StringBuilder array = new StringBuilder(100);
            asignaciones.add(array);
            array.append(var).append(" = new Object[]{");
            for (int i : noAtomica) {
                Simbolo exp = valores.get(i);
                array.append(exp.getCodigoGenerado());
                array.append(",");
                //Substituto
                Simbolo aux = new SimboloAux(exp.getTipo());
                StringBuilder nuevaExp = new StringBuilder(50);
                nuevaExp.append("(").append(Tipos.declaracion(aux.getTipo())).append(")");
                nuevaExp.append(var).append("[").append(i).append("]");
                aux.setCodigoGenerado(nuevaExp);
                valores.set(i, aux);
            }
            array.setCharAt(array.length() - 1, '}');
        }
    }

}
