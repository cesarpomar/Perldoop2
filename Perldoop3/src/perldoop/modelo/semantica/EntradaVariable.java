package perldoop.modelo.semantica;

/**
 * Variable almacenada en la tabla de simbolos
 *
 * @author César Pomar
 */
public final class EntradaVariable {

    private String identificador;
    private Tipo tipo;
    private boolean publica;
    private int nivel;
    private String alias;
    private boolean conflicto;

    /**
     * Contruye una entrada de la tabla para una variable
     *
     * @param identificador Identificador
     * @param tipo Tipo
     * @param publica Publica
     */
    public EntradaVariable(String identificador, Tipo tipo, boolean publica) {
        this.identificador = identificador;
        this.tipo = tipo;
        this.publica = publica;
    }

    /**
     * Contruye una entrada de la tabla para una variable con un alias
     *
     * @param identificador Identificador
     * @param tipo Tipo
     * @param alias Alias
     * @param publica Publica
     */
    public EntradaVariable(String identificador, Tipo tipo, String alias, boolean publica) {
        this(identificador, tipo, publica);
        this.alias = alias;
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
     * Obtiene el tipo
     *
     * @return Tipo
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo
     *
     * @param tipo Tipo
     */
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    /**
     * Comprueba si la variable es pública
     *
     * @return Es publica
     */
    public boolean isPublica() {
        return publica;
    }

    /**
     * Establece si la variable es pública
     *
     * @param publica Es publica
     */
    public void setPublica(boolean publica) {
        this.publica = publica;
    }

    /**
     * Obtiene el nivel
     *
     * @return Nivel
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * Establece el nivel
     *
     * @param nivel Nivel
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    /**
     * Comprueba si la variable es global nivel==0
     *
     * @return Es global
     */
    public boolean isGlobal() {
        return nivel == 0;
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
