package perldoop.lib;

/**
 * Tipo de dato lista extendido para ser anidado con otras listas sin ser inicializado
 *
 * @author César Pomar
 * @param <T> Tipo de los elementos
 */
public final class SmartPerlList<T> extends PerlList<T> {

    private int level;

    /**
     * Crea una lista vacia
     *
     * @param level Nivel de anidamiento
     */
    public SmartPerlList(int level) {
        super();
        this.level = level;
    }

    /**
     * Crea una lista con una capacidad inicial
     *
     * @param initialCapacity Capacidad inicial
     * @param level Nivel de anidamiento
     */
    public SmartPerlList(int level, int initialCapacity) {
        super(initialCapacity);
        this.level = level;
    }

    @Override
    public T get(int index) {
        T get = super.get(index);
        if (get == null && level > 1) {
            get = set(index, (T) new SmartPerlList(level - 1));
        }
        return get;
    }

}
