package perldoop.modelo.semantica;

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
    private String alias;

    /**
     * Constructor del contexto 
     * @param alias Alias de la variable
     */
    public Contexto(String alias) {
        this.alias = alias;
    }
    
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
    
    /**
     * Obtiene el alias de la variable
     *
     * @return Alias de la variable
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Establece el alias de la variable
     *
     * @param alias Alias de la variable
     */
    public void setAlias(String alias) {
        this.alias = alias;
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
