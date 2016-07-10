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

    @Override
    public T put(String key, T value) {
        super.put(key, value);
        return value;
    }

}
