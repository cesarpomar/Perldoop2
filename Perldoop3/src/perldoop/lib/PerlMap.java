package perldoop.lib;

import java.util.HashMap;
import java.util.Map;

/**
 * Tipo de dato mapa
 *
 * @author César Pomar
 * @param <T> Tipo del valor
 */
public final class PerlMap<T> extends HashMap<String, T> {

    /**
     * Crea un mapa vacio
     */
    public PerlMap() {
        super();
    }

    /**
     * Crea un mapa con una capacidad y un factor de crecimiento
     *
     * @param initialCapacity Capacidad inicial
     * @param loadFactor Factor de crecicimiento
     */
    public PerlMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * Crea un mapa con una capacidad inicial
     *
     * @param initialCapacity Capacidad inicial
     */
    public PerlMap(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Crea un mapa con los elementos de otro mapa
     *
     * @param m Mapa
     */
    public PerlMap(Map<? extends String, ? extends T> m) {
        super(m);
    }

    /**
     * Crea un mapa partiendo de un array de claves y otro de valores
     * @param claves Array de claves
     * @param valores Array de valores
     */
    public PerlMap(String[] claves, T[] valores) {
        super(claves.length * 2);
        for (int i = 0; i < claves.length; i++) {
            put(claves[i], valores[i]);
        }
    }

    @Override
    public T put(String key, T value) {
        super.put(key, value);
        return value;
    }

}
