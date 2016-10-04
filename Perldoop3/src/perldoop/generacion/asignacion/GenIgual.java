package perldoop.generacion.asignacion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import perldoop.generacion.util.Casting;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.acceso.Acceso;
import perldoop.modelo.arbol.acceso.AccesoCol;
import perldoop.modelo.arbol.acceso.AccesoColRef;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.expresion.ExpAcceso;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.ExpFuncion;
import perldoop.modelo.arbol.expresion.ExpFuncion5;
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
        if (s.getDerecha() instanceof ExpFuncion || s.getDerecha() instanceof ExpFuncion5) {
            Simbolo retorno = new Terminal();
            if (s.getIzquierda().getTipo().isColeccion()) {
                retorno.setCodigoGenerado(new StringBuilder(s.getDerecha().getCodigoGenerado()).append("[0]"));
                retorno.setTipo(new Tipo(Tipo.BOX));
            } else {
                retorno = s.getDerecha();
            }
            s.setCodigoGenerado(asignacion(s.getIzquierda(), s.getIgual().getComentario(), retorno));
        } else {
            s.setCodigoGenerado(asignacion(s.getIzquierda(), s.getIgual().getComentario(), s.getDerecha()));
        }
    }

    /**
     * Asignación multiple, es decir se asignan multiples variables
     *
     * @param s Simbolo de asignacion
     */
    private void multiple(Igual s) {
        List<Expresion> variables = ((ColParentesis) ((ExpColeccion) s.getIzquierda()).getColeccion()).getLista().getExpresiones();
        List<StringBuilder> asignaciones = new ArrayList<>(variables.size());
        if (s.getDerecha() instanceof ExpColeccion) {
            List<Simbolo> valores = (List) ((ColParentesis) ((ExpColeccion) s.getDerecha()).getColeccion()).getLista().getExpresiones();
            atomicidad(asignaciones, variables, valores);
            for (int i = 0; i < variables.size(); i++) {
                asignaciones.add(asignacion(variables.get(i), "", valores.get(i)));
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
                valor = s.getCodigoGenerado().toString();
            }
            Terminal coleccion = new Terminal();
            coleccion.setTipo(s.getIzquierda().getTipo().getSubtipo(1));
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
                Terminal var = new Terminal();
                var.setTipo(entrada.getTipo());
                var.setCodigoGenerado(new StringBuilder(entrada.getAlias()));
                valor = Casting.toInteger(var).toString();
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
        Terminal term = new Terminal();
        term.setTipo(s.getIzquierda().getTipo());
        term.setCodigoGenerado(ini);
        s.setCodigoGenerado(asignacion(s.getIzquierda(), s.getIgual().getComentario(), term));
    }

    /**
     * Realiza la asignacion
     *
     * @param izq Simbolo izquierda
     * @param igual Comentario simbolo igual
     * @param der Simbolo derecho
     * @return Codigo asignacion
     */
    private StringBuilder asignacion(Simbolo izq, String igual, Simbolo der) {
        StringBuilder codigo = new StringBuilder(100);
        Simbolo der2 = der;
        //Copiar colecciones y Referencias
        if (der.getTipo().isColeccion() && Buscar.isVariable(der)) {
            der2 = new Terminal();
            der2.setTipo(der.getTipo());
            StringBuilder copia = new StringBuilder(50 + der.getCodigoGenerado().length());
            copia.append("Pd.copy(").append(der.getCodigoGenerado()).append(")");
            der2.setCodigoGenerado(copia);
        }
        //Usar una funcion en lugar del operador =
        if (izq instanceof ExpAcceso) {
            Acceso acceso = ((ExpAcceso) izq).getAcceso();
            Tipo t = acceso.getExpresion().getTipo();
            Tipo st = t.getSubtipo(1);
            if (acceso.getTipo().isColeccion() || t.isList() || t.isMap() || (t.isRef() && (st.isList() || st.isMap()))) {
                codigo.append(izq.getCodigoGenerado())
                        .append(igual)
                        .append(Casting.casting(der2, izq.getTipo())).append(")");
                return codigo;
            }
        }
        codigo.append(izq.getCodigoGenerado())
                .append("=").append(igual)
                .append(Casting.casting(der2, izq.getTipo()));
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
     * Asegura la atomicidad entre las asignaciones
     *
     * @param asignaciones Asignaciones
     * @param variables Variables
     * @param valores Valores
     */
    public void atomicidad(List<StringBuilder> asignaciones, List<Expresion> variables, List<Simbolo> valores) {
        HashSet<String> escrituras = new HashSet<>(variables.size());
        List<Integer> noAtomica = new ArrayList<>(variables.size());
        for (Expresion var : variables) {
            Variable simbolo = Buscar.buscarVariable(var);
            String alias = tabla.getTablaSimbolos().buscarVariable(simbolo.getVar().getValor(), Buscar.getContexto(simbolo)).getAlias();
            if (simbolo instanceof VarMy || simbolo instanceof VarOur) {
                StringBuilder ini = new StringBuilder(50);
                ini.append(alias).append("=").append("null");
                asignaciones.add(ini);
            }
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
            String aux = tabla.getGestorReservas().getAux();
            StringBuilder dec = new StringBuilder(20);
            dec.append("Object[] ").append(aux).append(";");
            tabla.getDeclaraciones().add(dec);
            //Substituimos
            StringBuilder array = new StringBuilder(100);
            asignaciones.add(array);
            array.append(aux).append(" = new Object[]{");
            for (int i : noAtomica) {
                Simbolo exp = valores.get(i);
                array.append(exp.getCodigoGenerado());
                array.append(",");
                //Substituto
                Terminal t = new Terminal();
                t.setTipo(exp.getTipo());
                StringBuilder nuevaExp = new StringBuilder(50);
                nuevaExp.append("(").append(Tipos.declaracion(t.getTipo())).append(")");
                nuevaExp.append(aux).append("[").append(i).append("]");
                t.setCodigoGenerado(nuevaExp);
                valores.set(i, t);
            }
            array.setCharAt(array.length() - 1, '}');
        }
    }

}
