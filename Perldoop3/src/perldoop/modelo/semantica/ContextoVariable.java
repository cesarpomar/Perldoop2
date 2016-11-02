package perldoop.modelo.semantica;

/**
 * Clase para almacenar los tres tipos contexto que puede tener un
 * identificador.
 *
 * @author César Pomar
 */
public final class ContextoVariable {
    
    private EntradaVariable escalar;
    private EntradaVariable array;
    private EntradaVariable hash;

    /**
     * Constructor del contexto 
     */
    public ContextoVariable() {
    }
    
    /**
     * Obtiene el contexto escalar
     *
     * @return Escalar
     */
    public EntradaVariable getEscalar() {
        return escalar;
    }

    /**
     * Establece el contexto escalar
     *
     * @param escalar Escalar
     */
    public void setEscalar(EntradaVariable escalar) {
        this.escalar = escalar;
    }

    /**
     * Obtiene el contexto array
     *
     * @return Array
     */
    public EntradaVariable getArray() {
        return array;
    }

    /**
     * Establece el contexto array
     *
     * @param array Array
     */
    public void setArray(EntradaVariable array) {
        this.array = array;
    }

    /**
     * Obtiene el contexto Hash
     *
     * @return hash
     */
    public EntradaVariable getHash() {
        return hash;
    }

    /**
     * Establece el contexto hash
     *
     * @param hash Hash
     */
    public void setHash(EntradaVariable hash) {
        this.hash = hash;
    }
    
    /**
     * Obtiene el número de contextos de la variable
     * 
     * @return Número de contextos
     */
    public int size(){
        int size = 0;
        if(escalar!=null)size++;
        if(array!=null)size++;
        if(hash!=null)size++;
        return size;
    }

}
