package perldoop.lib;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Tipo de dato lista
 *
 * @author César Pomar
 * @param <T> Tipo de los elementos
 */
public final class PerlList<T> extends ArrayList<T> {

    /**
     * Crea una lista vacia
     */
    public PerlList() {
        super();
    }

    /**
     * Crea una lista con una capacidad inicial
     *
     * @param initialCapacity Capacidad inicial
     */
    public PerlList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Crea una lista con una capacidad inicial
     *
     * @param array Crea una lista con los elementos del array
     */
    public PerlList(T[] array) {
        super(array.length*2);
        for (T e : array) {
            this.add(e);
        }
    }

    /**
     * Crea una lista con los elementos de una colección
     *
     * @param c Colección
     */
    public PerlList(Collection<? extends T> c) {
        super(c);
    }

    @Override
    public T set(int index, T element) {
        if(index == size()){
            super.add(element);
        }else{
            super.set(index, element);
        }
        return element;
    }

}
