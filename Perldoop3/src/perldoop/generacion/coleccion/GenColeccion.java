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
     * @param tabla
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

    private StringBuilder genArray(Lista l, Tipo t) {
        StringBuilder codigo;
        if (buscarColeccion(l)) {
            return genConcat(l.getExpresiones(), t).append(".toArray(").append(Tipos.declaracion(t)).append(".class)");
        }
        codigo = new StringBuilder(200);
        Tipo st = t.getSubtipo(1);
        if (st.isArrayOrList() || st.isMap()) {
            st.add(0, Tipo.REF);
        }
        Iterator<Expresion> it = l.getExpresiones().iterator();
        codigo.append(Tipos.inicializacion(t)).append("{");
        while (it.hasNext()) {
            codigo.append(Casting.casting(it.next(), st));
            if (it.hasNext()) {
                codigo.append(", ");
            }
        }
        codigo.append("}");
        return codigo;
    }

    private StringBuilder genList(Lista l, Tipo t) {
        StringBuilder codigo;
        if (buscarColeccion(l)) {
            return genConcat(l.getExpresiones(), t).append(".toList()");
        }
        codigo = new StringBuilder(200);
        Tipo st = t.getSubtipo(1);
        if (st.isArrayOrList() || st.isMap()) {
            st.add(0, Tipo.REF);
        }
        Iterator<Expresion> it = l.getExpresiones().iterator();
        codigo.append("Pd.list(").append(Tipos.inicializacion(t.getSubtipo(1).add(0, Tipo.ARRAY))).append("{");
        while (it.hasNext()) {
            codigo.append(Casting.casting(it.next(), st));
            if (it.hasNext()) {
                codigo.append(", ");
            }
        }
        codigo.append("})");
        return codigo;
    }

    private void genMapAux(StringBuilder codigo, List l, Tipo t) {
        Tipo st = t.getSubtipo(1);
        if (st.isArrayOrList() || st.isMap()) {
            st.add(0, Tipo.REF);
        }
        Iterator<Expresion> it = l.iterator();
        boolean clave = true;
        codigo.append("Perl.map(");
        StringBuilder claves = new StringBuilder(100);
        StringBuilder valores = new StringBuilder(100);
        while (it.hasNext()) {
            if (clave) {
                claves.append(Casting.casting(it.next(), new Tipo(Tipo.STRING)));
                if (!it.hasNext()) {
                    //Error clave sin valor
                }
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
    }

    private StringBuilder genMap(Lista l, Tipo t) {
        StringBuilder codigo;
        if (buscarColeccion(l)) {
            List<Expresion> cols = new ArrayList<>(10);
            List<Expresion> elems = new ArrayList<>(10);
            for (Expresion exp : l.getExpresiones()) {
                if (exp.getTipo().isColeccion()) {
                    cols.add(exp);
                } else {
                    elems.add(exp);
                }
            }
            codigo = genConcat(cols, t);
            if (!elems.isEmpty()) {
                codigo.append(".append(");
                genMapAux(codigo, l.getExpresiones(), t);
                codigo.append(")");
            }
            return codigo.append(".toMap()");
        } else {
            codigo = new StringBuilder(200);
            genMapAux(codigo, l.getExpresiones(), t);
        }
        return codigo;
    }

    private StringBuilder genConcat(List l, Tipo t) {
        StringBuilder codigo = new StringBuilder(200);
        List<Expresion> elems = new ArrayList<>(20);
        Iterator<Expresion> it = l.iterator();
        codigo.append("Pd.union()");
        Tipo st = t.getSubtipo(1);
        Tipo array = st.getSubtipo(0).add(0, Tipo.ARRAY);
        if (st.isArrayOrList() || st.isMap()) {
            st.add(0, Tipo.REF);
        }
        while (it.hasNext()) {
            Expresion exp = it.next();
            boolean coleccion = exp.getTipo().isColeccion();
            if (!coleccion) {
                elems.add(exp);
                continue;
            }
            if (!elems.isEmpty() && (exp.getTipo().isColeccion() || !it.hasNext())) {
                codigo.append(Tipos.inicializacion(array)).append("{");
                Iterator<Expresion> it2 = elems.iterator();
                while (it2.hasNext()) {
                    codigo.append(Casting.casting(it2.next(), st));
                    if (it2.hasNext()) {
                        codigo.append(",");
                    }
                }
                codigo.append("}");
            }
            if (exp.getTipo().getSubtipo(1).equals(st)) {
                codigo.append(".append(").append(exp.getCodigoGenerado()).append(")");
            } else {
                codigo.append(".append(").append(Casting.casting(exp, t)).append(")");
            }
        }
        return codigo;
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

    public void visitar(ColParentesis s) {
        //Expresion entre parentesis
        if (s.getLista().getElementos().size() == 1) {
            s.setCodigoGenerado(new StringBuilder(s.getLista().getExpresiones().get(0).getCodigoGenerado()));
            return;
        }
        //Colecciones
        Tipo t = s.getTipo();
        if (t.isArray()) {
            s.setCodigoGenerado(genArray(s.getLista(), t));
        } else if (t.isList()) {
            s.setCodigoGenerado(genList(s.getLista(), t));
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
        } else if (t.isArray()) {
            s.setCodigoGenerado(genArray(s.getLista(), t));
        } else if (t.isList()) {
            s.setCodigoGenerado(genList(s.getLista(), t));
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
