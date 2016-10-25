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
import perldoop.modelo.arbol.acceso.AccesoRefArray;
import perldoop.modelo.arbol.acceso.AccesoRefEscalar;
import perldoop.modelo.arbol.acceso.AccesoRefMap;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.coleccion.ColLlave;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.flujo.Return;
import perldoop.modelo.arbol.funcion.Funcion;
import perldoop.modelo.arbol.lista.Lista;
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
    private void genRef(Coleccion c, Tipo t) {
        Simbolo uso = Buscar.getPadre(c, 2);
        //Si no va para otra colecion o acceso es una referencia
        List<Simbolo> acceso = Buscar.getCamino(c, ExpColeccion.class, Lista.class, ColLlave.class, ExpColeccion.class, Acceso.class);
        if (!(uso instanceof Coleccion && uso.getTipo() != null && uso.getTipo().isColeccion())
                && !(c.getPadre() instanceof Acceso || !acceso.isEmpty())) {
            StringBuilder diamante = new StringBuilder();
            diamante.append("new Ref<").append(Tipos.declaracion(t)).append(">(");
            c.getCodigoGenerado().insert(0, diamante).append(")");
        }
    }

    /**
     * Genera la coleccion como Array o List
     *
     * @param l Lista de expresiones
     * @param t Tipo
     * @return Codigo
     */
    private StringBuilder genArrayList(Lista l, Tipo t) {
        if (l.getExpresiones().isEmpty()) {
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
        List<Expresion> expresiones = new ArrayList<>(l.getExpresiones().size());
        Iterator<Expresion> it = l.getExpresiones().iterator();
        while (it.hasNext()) {
            Expresion exp = it.next();
            boolean iscoleccion = exp.getTipo().isColeccion();
            if (!iscoleccion) {
                expresiones.add(exp);
            }
            if (!expresiones.isEmpty() && (!it.hasNext() || exp.getTipo().isColeccion())) {
                //Creamos una coleccion con todos las expresiones
                StringBuilder coleccion = new StringBuilder(100);
                coleccion.append(inicializacion).append("{");
                Iterator<Expresion> it2 = expresiones.iterator();
                while (it2.hasNext()) {
                    coleccion.append(Casting.casting(it2.next(), st));
                    if (it2.hasNext()) {
                        coleccion.append(",");
                    }
                }
                coleccion.append("}");
                colecciones.add(coleccion);
                expresiones.clear();
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

    public void visitar(ColParentesis s) {
        //Expresion entre parentesis
        Simbolo uso = Buscar.getPadre(s, 1);
        if (s.getLista().getElementos().size() == 1 && !(uso instanceof Funcion) && !(uso instanceof Return)) {
            Expresion exp = s.getLista().getExpresiones().get(0);
            StringBuilder codigo = new StringBuilder(50);
            codigo.append(Casting.casting(exp, s.getTipo()));
            s.setCodigoGenerado(codigo);
        } else {
            //Colecciones
            Tipo t = s.getTipo();
            if (t.isArrayOrList()) {
                s.setCodigoGenerado(genArrayList(s.getLista(), t));
            } else if (t.isMap()) {
                s.setCodigoGenerado(genMap(s.getLista(), t));
            }
        }
        if (!s.isVirtual()) {
            s.getCodigoGenerado().insert(0, s.getParentesisI().getComentario());
            s.getCodigoGenerado().append(s.getParentesisD().getComentario());
        }
    }

    public void visitar(ColCorchete s) {
        Simbolo padre = s.getPadre();
        if ((padre instanceof AccesoCol || padre instanceof AccesoColRef) && s.getLista().getExpresiones().size() == 1) {
            Expresion exp = s.getLista().getExpresiones().get(0);
            StringBuilder codigo = new StringBuilder(50);
            codigo.append(s.getCorcheteI().getComentario()).append(Casting.casting(exp, s.getTipo())).append(s.getCorcheteD().getComentario());
            s.setCodigoGenerado(codigo);
        } else {
            Tipo t = s.getTipo();
            if (t.isRef()) {
                t = t.getSubtipo(1);
            }
            s.setCodigoGenerado(genArrayList(s.getLista(), t));
            s.getCodigoGenerado().insert(0, s.getCorcheteI().getComentario());
            s.getCodigoGenerado().append(s.getCorcheteD().getComentario());
            genRef(s, t);
        }
    }

    public void visitar(ColLlave s) {
        Simbolo uso = s.getPadre().getPadre();
         if ((s.getPadre() instanceof AccesoCol || s.getPadre() instanceof AccesoColRef) && s.getLista().getExpresiones().size() == 1) {
            Expresion exp = s.getLista().getExpresiones().get(0);
            StringBuilder codigo = new StringBuilder(50);
            codigo.append(s.getLlaveI().getComentario()).append(Casting.casting(exp, s.getTipo())).append(s.getLlaveD().getComentario());
            s.setCodigoGenerado(codigo);
        }else if (uso instanceof AccesoRefEscalar || uso instanceof AccesoRefArray || uso instanceof AccesoRefMap) {
            Expresion exp = s.getLista().getExpresiones().get(0);
            StringBuilder codigo = new StringBuilder(50);
            codigo.append(s.getLlaveI().getComentario()).append(Casting.casting(exp, s.getTipo())).append(s.getLlaveD().getComentario());
            s.setCodigoGenerado(codigo);
        } else {
            Tipo t = s.getTipo();
            if (t.isRef()) {
                t = t.getSubtipo(1);//Quitar ref
            }
            if (t.isArrayOrList()) {//En caso de multiacceso
                s.setCodigoGenerado(genArrayList(s.getLista(), t));
            } else if (t.isMap()) {//Creacion de mapa
                s.setCodigoGenerado(genMap(s.getLista(), t));
            }
            s.getCodigoGenerado().insert(0, s.getLlaveI().getComentario());
            s.getCodigoGenerado().append(s.getLlaveD().getComentario());
            genRef(s, t);
        }
    }

}
