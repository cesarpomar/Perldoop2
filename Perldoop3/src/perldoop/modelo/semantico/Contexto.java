package perldoop.modelo.semantico;

/**
 * Clase para almacenar los tres tipos contexto que puede tener un
 * identificador.
 *
 * @author César Pomar
 */
public final class Contexto {
    
    private EntradaTabla escalar;
    private EntradaTabla array;
    private EntradaTabla hash;

    /**
     * Obtiene el contexto escalar
     *
     * @return Escalar
     */
    public EntradaTabla getEscalar() {
        return escalar;
    }

    /**
     * Establece el contexto escalar
     *
     * @param escalar Escalar
     */
    public void setEscalar(EntradaTabla escalar) {
        this.escalar = escalar;
    }

    /**
     * Obtiene el contexto array
     *
     * @return Array
     */
    public EntradaTabla getArray() {
        return array;
    }

    /**
     * Establece el contexto array
     *
     * @param array Array
     */
    public void setArray(EntradaTabla array) {
        this.array = array;
    }

    /**
     * Obtiene el contexto Hash
     *
     * @return hash
     */
    public EntradaTabla getHash() {
        return hash;
    }

    /**
     * Establece el contexto hash
     *
     * @param hash Hash
     */
    public void setHash(EntradaTabla hash) {
        this.hash = hash;
    }

}
