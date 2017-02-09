package perldoop.lib;

import java.util.List;

/**
 * Tipo de dato mapa extendido para ser anidado con otros mapas sin ser inicializado
 *
 * @author César Pomar
 * @param <T> Tipo del valor
 */
public final class SmartPerlMap<T> extends PerlMap<T> {

    private int level;

    /**
     * Crea un mapa vacio
     *
     * @param level Nivel de anidamiento
     */
    public SmartPerlMap(int level) {
        super();
        this.level = level;
    }

    /**
     * Crea un mapa con una capacidad inicial
     *
     * @param initialCapacity Capacidad inicial
     * @param level Nivel de anidamiento
     */
    public SmartPerlMap(int level, int initialCapacity) {
        super(initialCapacity);
        this.level = level;
    }

    @Override
    public T get(List<String> keys) {
        T get = super.get(keys);
        if (get == null && level > 1) {
            get = put(keys, (T) new SmartPerlMap(level - 1));
        }
        return get;
    }

    @Override
    public T get(Object key) {
        T get = super.get(key);
        if (get == null && level > 1) {
            get = put(key.toString(), (T) new SmartPerlMap(level - 1));
        }
        return get;
    }

}
