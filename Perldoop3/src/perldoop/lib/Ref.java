package perldoop.lib;

/**
 * Alamacena otra clase imitando una referencia
 *
 * @author César Pomar
 * @param <T> Tipo del Objeto almacenado
 */
public final class Ref<T> {

    private T value;

    /**
     * Contruye la referencia
     *
     * @param value Objeto a almacenar
     */
    public Ref(T value) {
        this.value = value;
    }

    /**
     * Obtiene el objeto almacenado
     *
     * @return Objeto almacenado
     */
    public T get() {
        return value;
    }

    /**
     * Establece el objeto almacenado
     *
     * @param value Objeto para almacenar
     */
    public void set(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value instanceof Object[] || value instanceof PerlList) {
            return "ARRAY(0x" + super.toString().substring(1) + ")";
        } else if (value instanceof PerlMap) {
            return "HASH(0x" + super.toString().substring(1) + ")";
        } else {
            return value.toString();
        }
    }

}
