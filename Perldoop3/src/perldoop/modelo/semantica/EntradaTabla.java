package perldoop.modelo.semantica;

/**
 * Variable almacenada en la tabla de simbolos
 *
 * @author César
 */
public final class EntradaTabla {

    private String identificador;
    private Tipo tipo;
    private boolean publica;
    private int nivel;
    private String alias;

    /**
     * Contruye una entrada de la tabla
     *
     * @param identificador Identificador
     * @param tipo Tipo
     * @param publica Publica
     */
    public EntradaTabla(String identificador, Tipo tipo, boolean publica) {
        this.identificador = identificador;
        this.tipo = tipo;
        this.publica = publica;
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

}
