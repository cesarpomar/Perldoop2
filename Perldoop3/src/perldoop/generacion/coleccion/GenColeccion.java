package perldoop.generacion.coleccion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import perldoop.generacion.util.Casting;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
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
            codigo = genConcat(l, t);
            codigo.insert(0, new StringBuilder(30).append("Perl.union(").append(Tipos.declaracion(t)).append(".class, "));
            return codigo;
        }
        codigo = new StringBuilder(100);
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
        Tipo t2 = t.getSubtipo(1).add(0, Tipo.ARRAY);
        StringBuilder codigo = genArray(l, t2).insert(0, "Perl.list(").append(")");
        return codigo;
    }

    private StringBuilder genMap(Lista l, Tipo t) {
        StringBuilder codigo;
        if (buscarColeccion(l)) {
            codigo = genConcat(l, t);
            codigo.insert(0, "Perl.map(Perl.union(").append(")");
            return codigo;
        }
        codigo = new StringBuilder(100);
        Tipo st = t.getSubtipo(1);
        if (st.isArrayOrList() || st.isMap()) {
            st.add(0, Tipo.REF);
        }
        Iterator<Expresion> it = l.getExpresiones().iterator();
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
        codigo.append(Tipos.inicializacion(st)).append("{").append(valores).append("})");
        codigo.append(")");
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

    private void genConcatAux(StringBuilder codigo, Tipo t, Tipo st, List<Expresion> elems) {
        if (elems.size() == 1 && elems.get(0).getTipo().isRef() && elems.get(0).getTipo().getSubtipo(1).isArray()) {
            codigo.append("Perl.partA(");
        } else {
            codigo.append("Perl.part(");
        }
        for (Expresion exp2 : elems) {
            codigo.append(Casting.casting(exp2, st)).append(", ");
        }
        codigo.setLength(codigo.length() - 2);

        codigo.append("), ");
    }

    private StringBuilder genConcat(Lista l, Tipo t) {
        StringBuilder codigo = new StringBuilder(100);
        List<Expresion> elems = new ArrayList<>(20);
        Tipo st = t.getSubtipo(1);
        if (st.isArrayOrList() || st.isMap()) {
            st.add(0, Tipo.REF);
        }
        for (Expresion exp : l.getExpresiones()) {
            if (exp.getTipo().isColeccion()) {
                if (!elems.isEmpty()) {
                    genConcatAux(codigo, t, st, elems);
                }
                if (exp.getTipo().getSubtipo(1).equals(st)) {
                    codigo.append("Perl.part(").append(exp.getCodigoGenerado()).append("), ");
                } else {
                    codigo.append("Perl.part(").append(Casting.casting(exp, t)).append("), ");
                }
                elems.clear();
            } else {
                elems.add(exp);
            }
        }
        if (!elems.isEmpty()) {
            if (!elems.isEmpty()) {
                genConcatAux(codigo, t, st, elems);
            }
        }
        codigo.setLength(codigo.length() - 2);
        codigo.append(")");
        return codigo;
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
        if (t.isArray()) {
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
        if (t.isMap()) {
            s.setCodigoGenerado(genMap(s.getLista(), t));
        }
        genRef(s, t);
    }

}
