package perldoop.modelo.semantica;

/**
 * Función almacenada en la tabla de simbolos
 *
 * @author César Pomar
 */
public final class EntradaFuncion {

    private String identificador;
    private String alias;
    private boolean conflicto;

    /**
     * Contruye una entrada de la tabla pra una función
     *
     * @param identificador Identificador
     */
    public EntradaFuncion(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene el identificador
     *
     * @return Identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece el identificador
     *
     * @param identificador Identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
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
     * Obtiene si la variable entra en conflicto con otra
     *
     * @return Existe conflicto
     */
    public boolean isConflicto() {
        return conflicto;
    }

    /**
     * Estable si la variable entra en conflicto con otra
     *
     * @param conflicto Existe conflicto
     */
    public void setConflicto(boolean conflicto) {
        this.conflicto = conflicto;
    }

}
