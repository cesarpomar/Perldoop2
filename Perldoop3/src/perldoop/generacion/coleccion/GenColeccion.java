package perldoop.generacion.coleccion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import perldoop.generacion.util.Casting;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.acceso.AccesoCol;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.coleccion.ColLlave;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.lista.Lista;
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
     * Busca si la colección contiene colecciones anidadas
     *
     * @param l Lista
     * @return Colecciones anidadas
     */
    private boolean buscarColeccion(Lista l) {
        for (Expresion exp : l.getExpresiones()) {
            if (exp.getTipo().isColeccion()) {
                return true;
            }
        }
        return false;
    }

    private void genRef(Coleccion c, Tipo t) {
        Simbolo uso = Buscar.getPadre(c, 2);
        //Si no va para otra colecion es una referencia
        if (!(uso instanceof Coleccion) && !(c.getPadre() instanceof AccesoCol)) {
            uso = Buscar.getPadre(uso, 2);
            StringBuilder lambda = new StringBuilder();
            if (!(uso instanceof Igual)) {
                lambda = Tipos.declaracion(t);
            }
            lambda.insert(0, "new Ref<").append(">(");
            c.getCodigoGenerado().insert(0, lambda).append(")");
        }
    }

    private StringBuilder genArrayList(Lista l, Tipo t) {
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
            //Si solo hay una coleccion, como mucho habra que convertirla a list
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

    private StringBuilder genMapExps(List<Expresion> l, Tipo st) {
        StringBuilder codigo = new StringBuilder(100);
        Iterator<Expresion> it = l.iterator();
        boolean clave = true;
        codigo.append("Perl.map(");
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
        st.add(0, Tipo.ARRAY);//Usamos el mismo convirtiendolo en array
        codigo.append("new String[]{").append(claves).append("}, ");
        codigo.append(Tipos.inicializacion(st)).append("{").append(valores).append("}");
        codigo.append(")");
        return codigo;
    }

    private StringBuilder genMap(Lista l, Tipo t) {
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
        if (s.getLista().getElementos().size() == 1) {
            s.setCodigoGenerado(new StringBuilder(s.getLista().getExpresiones().get(0).getCodigoGenerado()));
            return;
        }
        //Colecciones
        Tipo t = s.getTipo();
        if (t.isArrayOrList()) {
            s.setCodigoGenerado(genArrayList(s.getLista(), t));
        } else if (t.isMap()) {
            s.setCodigoGenerado(genMap(s.getLista(), t));
        }
    }

    public void visitar(ColCorchete s) {
        Tipo t = s.getTipo();
        if (t.isRef()) {
            t = t.getSubtipo(1);//Quitar ref
        }
        //Colecciones
        if (s.getLista().getElementos().size() == 1) {
            s.setCodigoGenerado(new StringBuilder(s.getLista().getExpresiones().get(0).getCodigoGenerado()));
        } else {
            s.setCodigoGenerado(genArrayList(s.getLista(), t));
        }
        genRef(s, t);
    }

    public void visitar(ColLlave s) {
        Tipo t = s.getTipo();
        if (t.isRef()) {
            t = t.getSubtipo(1);//Quitar ref
        }
        if (s.getLista().getElementos().size() == 1) {
            s.setCodigoGenerado(new StringBuilder(s.getLista().getExpresiones().get(0).getCodigoGenerado()));
        } else if (t.isMap()) {
            s.setCodigoGenerado(genMap(s.getLista(), t));
        }
        genRef(s, t);
    }

}
