package perldoop.generacion.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.coleccion.ColLlave;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase para iterar en colecciones y encapsular los elementos ajenos a las expresiones
 *
 * @author César Pomar
 */
public final class ColIterator implements Iterable<Expresion>, Iterator<Expresion> {

    private List<Simbolo> pila;
    private Expresion exp;
    private StringBuilder comentario;

    /**
     * Crea un iterador vacio
     */
    public ColIterator() {
        pila = new ArrayList<>(50);
        comentario = new StringBuilder(1000);
    }

    /**
     * Crea un iterador de colecciones
     *
     * @param col Coleccion
     */
    public ColIterator(Coleccion col) {
        this();
        if (col instanceof ColParentesis) {
            if (((ColParentesis) col).isVirtual()) {
                addPila(col.getLista().getElementos());
            } else {
                pila.add(((ColParentesis) col).getParentesisD());
                addPila(col.getLista().getElementos());
                pila.add(((ColParentesis) col).getParentesisI());
            }
        } else if (col instanceof ColCorchete) {
            pila.add(((ColCorchete) col).getCorcheteD());
            addPila(col.getLista().getElementos());
            pila.add(((ColCorchete) col).getCorcheteI());
        } else if (col instanceof ColLlave) {
            pila.add(((ColLlave) col).getLlaveD());
            addPila(col.getLista().getElementos());
            pila.add(((ColLlave) col).getLlaveI());
        }
        next();
    }

    /**
     * Añade a la pila una lista de simbolos
     *
     * @param ls Lista de simbolos
     */
    private void addPila(List<Simbolo> ls) {
        ListIterator<Simbolo> it = ls.listIterator(ls.size());
        while (it.hasPrevious()) {
            pila.add(it.previous());
        }
    }

    /**
     * Obtiene el comentario de los terminales entre expresiones
     *
     * @return Comentarios concatenados
     */
    public StringBuilder getComentario() {
        return comentario;
    }

    @Override
    public Iterator<Expresion> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return exp != null;
    }

    @Override
    public Expresion next() {
        Expresion aux = exp;
        exp = null;
        comentario.setLength(0);
        while (!pila.isEmpty()) {
            Simbolo actual = pila.remove(pila.size() - 1);
            if (actual instanceof Terminal) {
                comentario.append(((Terminal) actual).getComentario());
            } else if (actual instanceof ExpColeccion && ((ExpColeccion) actual).getValor() instanceof ColParentesis) {
                ColParentesis cp = (ColParentesis) ((ExpColeccion) actual).getValor();
                pila.add(cp.getParentesisD());
                addPila(cp.getLista().getElementos());
                pila.add(cp.getParentesisI());
            } else {
                exp = (Expresion) actual;
                break;
            }
        }
        return aux;
    }

}
