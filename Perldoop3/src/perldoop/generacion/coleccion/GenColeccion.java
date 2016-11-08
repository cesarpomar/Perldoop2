package perldoop.generacion.coleccion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import perldoop.generacion.util.Casting;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.acceso.Acceso;
import perldoop.modelo.arbol.acceso.AccesoCol;
import perldoop.modelo.arbol.acceso.AccesoColRef;
import perldoop.modelo.arbol.acceso.AccesoDesRef;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.coleccion.ColDec;
import perldoop.modelo.arbol.coleccion.ColDecMy;
import perldoop.modelo.arbol.coleccion.ColDecOur;
import perldoop.modelo.arbol.coleccion.ColLlave;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.Funcion;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Clase generadora de coleccion
 *
 * @author César Pomar
 */
public class GenColeccion {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenColeccion(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    /**
     * Genera la referencia cuando es necesario
     *
     * @param c Coleccion
     * @param t Tipo
     */
    private void genRef(Coleccion c) {
        //Si no es una referencia
        if (!c.getTipo().isRef()) {
            return;
        }
        if (c.getPadre() instanceof Expresion) {
            Simbolo uso = Buscar.getUso((Expresion) c.getPadre());
            //Si es un acceso
            if (!Buscar.getCamino(uso, ColLlave.class, ExpColeccion.class, Acceso.class).isEmpty()) {
                return;
            }
            //Si es una coleccion
            Coleccion col = Buscar.getColeccion(c);
            while (col != null) {
                if (col.getTipo() == null || col.getTipo().getSubtipo(1).isBox()) {
                    break;
                }
                if (!col.getTipo().equals(c.getTipo())) {
                    return;
                }
                col = Buscar.getColeccion(col);
            }
        }
        c.getCodigoGenerado().insert(0, Tipos.declaracion(c.getTipo()).insert(0, "new ").append('(')).append(')');
    }

    /**
     * Genera la coleccion como Array o List
     *
     * @param expresiones Expresiones
     * @param seps Separadores
     * @param t Tipo
     * @return Codigo
     */
    public static StringBuilder genArrayList(List<? extends Simbolo> expresiones, List<Terminal> seps, Tipo t) {
        if (expresiones.isEmpty()) {
            if (t.isArray()) {
                return Tipos.inicializacion(t, "0");
            }
            return Tipos.inicializacion(t);
        }
        //Tipo en Array
        Tipo ta = t.getSubtipo(1).add(0, Tipo.ARRAY);
        //Subtipo expresiones
        Tipo st = t.getSubtipo(1);
        if (st.isColeccion()) {
            st.add(0, Tipo.REF);
        }
        //Tipo que contendra las expresiones
        StringBuilder inicializacion = Tipos.inicializacion(ta);
        //Colecciones generadas en el proceso
        List<StringBuilder> colecciones = new ArrayList<>(10);
        //Expresiones consecutivas
        List<Simbolo> consecutivas = new ArrayList<>(expresiones.size());
        Iterator<? extends Simbolo> it = expresiones.iterator();
        while (it.hasNext()) {
            Simbolo exp = it.next();
            boolean iscoleccion = exp.getTipo().isColeccion();
            if (!iscoleccion) {
                consecutivas.add(exp);
            }
            if (!consecutivas.isEmpty() && (!it.hasNext() || exp.getTipo().isColeccion())) {
                //Creamos una coleccion con todos las expresiones
                StringBuilder coleccion = new StringBuilder(100);
                coleccion.append(inicializacion).append("{");
                Iterator<Simbolo> it2 = consecutivas.iterator();
                while (it2.hasNext()) {
                    coleccion.append(Casting.casting(it2.next(), st));
                    if (it2.hasNext()) {
                        coleccion.append(",");
                    }
                }
                coleccion.append("}");
                colecciones.add(coleccion);
                consecutivas.clear();
            }
            //Si es una coleccion habra que agregarla
            if (!iscoleccion) {
                continue;
            }
            if (exp.getTipo().getSubtipo(1).equals(st) && exp.getTipo().isArrayOrList()) {
                colecciones.add(exp.getCodigoGenerado());
            } else {
                colecciones.add(Casting.casting(exp, t));
            }
        }
        if (colecciones.size() == 1) {
            Terminal term = new Terminal();
            term.setCodigoGenerado(colecciones.get(0));
            term.setTipo(ta);
            return Casting.casting(term, t);
        } else {
            StringBuilder codigo = new StringBuilder(200);
            codigo.append("Pd.union()");
            for (StringBuilder col : colecciones) {
                codigo.append(".append(").append(col).append(")");
            }
            if (t.isArray()) {
                codigo.append(".toArray(").append(Tipos.declaracion(t)).append(".class)");
            } else {
                codigo.append(".toList()");
            }
            return codigo;
        }
    }

    /**
     * Genera una mapa con expresiones simple alternando clave y valor
     *
     * @param l Lista de expresiones
     * @param st Tipo contenido en el mapa
     * @return Codigo
     */
    private StringBuilder genMapExps(List<Expresion> l, Tipo st) {
        StringBuilder codigo = new StringBuilder(100);
        Iterator<Expresion> it = l.iterator();
        boolean clave = true;
        codigo.append(codigo).append("new PerlMap<");
        if (st.isRef()) {
            codigo.append(Tipos.declaracion(st.getSubtipo(1)));
        } else {
            codigo.append(Tipos.declaracion(st));
        }
        codigo.append(">(");
        StringBuilder claves = new StringBuilder(100);
        StringBuilder valores = new StringBuilder(100);
        while (it.hasNext()) {
            if (clave) {
                claves.append(Casting.casting(it.next(), new Tipo(Tipo.STRING)));
            } else {
                valores.append(Casting.casting(it.next(), st));
                if (it.hasNext()) {
                    claves.append(", ");
                    valores.append(", ");
                }
            }
            clave = !clave;
        }
        if (st.isRef()) {
            st = st.getSubtipo(1);
        }
        st.add(0, Tipo.ARRAY);//Usamos el mismo convirtiendolo en array
        codigo.append("new String[]{").append(claves).append("}, ");
        codigo.append(Tipos.inicializacion(st)).append("{").append(valores).append("}");
        codigo.append(")");
        return codigo;
    }

    /**
     * Crea un mapa usando una lista de expresiones
     *
     * @param l Lista de expresiones
     * @param t Tipo
     * @return Codigo
     */
    private StringBuilder genMap(Lista l, Tipo t) {
        if (l.getExpresiones().isEmpty()) {
            return Tipos.inicializacion(t);
        }
        //Subtipo expresiones
        Tipo st = t.getSubtipo(1);
        if (st.isColeccion()) {
            st.add(0, Tipo.REF);
        }
        //Colecciones generadas en el proceso
        List<StringBuilder> colecciones = new ArrayList<>(10);
        //Expresiones consecutivas
        List<Expresion> expresiones = new ArrayList<>(l.getExpresiones().size());
        Iterator<Expresion> it = l.getExpresiones().iterator();
        while (it.hasNext()) {
            Expresion exp = it.next();
            boolean iscoleccion = exp.getTipo().isColeccion();
            if (!iscoleccion) {
                expresiones.add(exp);
            }
            if (!expresiones.isEmpty() && (!it.hasNext() || exp.getTipo().isColeccion())) {
                colecciones.add(genMapExps(expresiones, st));
            }
            //Si es una coleccion habra que agregarla
            if (!iscoleccion) {
                continue;
            }
            if (exp.getTipo().equals(t) && exp.getTipo().isMap()) {
                colecciones.add(exp.getCodigoGenerado());
            } else {
                colecciones.add(Casting.casting(exp, t));
            }
        }
        if (colecciones.size() == 1) {
            return colecciones.get(0);
        } else {
            StringBuilder codigo = new StringBuilder(200);
            codigo.append("Pd.union()");
            for (StringBuilder col : colecciones) {
                codigo.append(".append(").append(col).append(")");
            }
            codigo.append(".toMap()");
            return codigo;
        }
    }

    /**
     * Concatena las declaraciones
     *
     * @param v Coleccion de variables
     */
    private void genDec(ColDec c) {
        if (!(Buscar.getUso((Expresion) c.getPadre()) instanceof StcLista)) {
            visitar(c);
            c.getCodigoGenerado().insert(0, c.getOperador().getComentario());
            return;
        }
        StringBuilder codigo = new StringBuilder(100);
        Iterator<Expresion> itExp = c.getLista().getExpresiones().iterator();
        Iterator<Terminal> itSep = c.getLista().getSeparadores().iterator();
        codigo.append(c.getOperador().getComentario());
        codigo.append(c.getParentesisI().getComentario());
        while (itExp.hasNext()) {
            codigo.append(itExp.next());
            if (itExp.hasNext()) {
                codigo.append(";");
            }
            if (itSep.hasNext()) {
                codigo.append(itSep.next().getComentario());
            }
        }
        codigo.append(c.getParentesisD().getComentario());
        c.setCodigoGenerado(codigo);
    }

    public void visitar(ColParentesis s) {
        if (s.getTipo() == null) {
            return;
        }
        StringBuilder codigo;
        //Si es un simple valor, podemos los parentesis y listo
        if (s.getLista().getExpresiones().size() == 1 && !(s.getPadre() instanceof Funcion) && !s.getTipo().isColeccion()) {
            codigo = new StringBuilder(100);
            codigo.append(Casting.casting(s.getLista().getExpresiones().get(0), s.getTipo()));
            if (!s.getLista().getSeparadores().isEmpty()) {
                codigo.append(s.getLista().getSeparadores().get(0).getComentario());
            }
            //Comprobar si hay parentesis
            if (!s.isVirtual()) {
                codigo.insert(0, s.getParentesisI());
                codigo.append(s.getParentesisD());
            }

        } else {
            if (s.getTipo().isMap()) {
                codigo = genMap(s.getLista(), s.getTipo());
            } else {
                codigo = genArrayList(s.getLista().getExpresiones(), s.getLista().getSeparadores(), s.getTipo());
            }
            //Comprobar si hay parentesis
            if (!s.isVirtual()) {
                codigo.insert(0, s.getParentesisI().getComentario());
                codigo.append(s.getParentesisD().getComentario());
            }
        }
        s.setCodigoGenerado(codigo);
    }

    public void visitar(ColDecMy s) {
        genDec(s);
    }

    public void visitar(ColDecOur s) {
        genDec(s);
    }

    public void visitar(ColCorchete s) {
        //Si es un acceso con un valor
        if ((s.getPadre() instanceof AccesoCol || s.getPadre() instanceof AccesoColRef) && s.getLista().getExpresiones().size() == 1) {
            Expresion exp = s.getLista().getExpresiones().get(0);
            s.setCodigoGenerado(Casting.casting(exp, s.getTipo()));
        } else {
            Tipo t = s.getTipo();
            if (t.isRef()) {
                t = t.getSubtipo(1);
            }
            s.setCodigoGenerado(genArrayList(s.getLista().getExpresiones(), s.getLista().getSeparadores(), t));
            genRef(s);
        }
        s.getCodigoGenerado().insert(0, s.getCorcheteI().getComentario());
        s.getCodigoGenerado().append(s.getCorcheteD().getComentario());
    }

    public void visitar(ColLlave s) {
        Simbolo uso = s.getPadre().getPadre();
        if (((s.getPadre() instanceof AccesoCol || s.getPadre() instanceof AccesoColRef) && s.getLista().getExpresiones().size() == 1)
                || (uso instanceof AccesoDesRef)) {
            Expresion exp = s.getLista().getExpresiones().get(0);
            s.setCodigoGenerado(Casting.casting(exp, s.getTipo()));
        } else {
            Tipo t = s.getTipo();
            if (t.isRef()) {
                t = t.getSubtipo(1);//Quitar ref
            }
            if (t.isArrayOrList()) {//En caso de multiacceso
                s.setCodigoGenerado(genArrayList(s.getLista().getExpresiones(), s.getLista().getSeparadores(), t));
            } else if (t.isMap()) {//Creacion de mapa
                s.setCodigoGenerado(genMap(s.getLista(), t));
            }
            genRef(s);
        }
        s.getCodigoGenerado().insert(0, s.getLlaveI().getComentario());
        s.getCodigoGenerado().append(s.getLlaveD().getComentario());
    }

}
