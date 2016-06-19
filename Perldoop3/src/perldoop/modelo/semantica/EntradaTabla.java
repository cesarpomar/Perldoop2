package perldoop.modelo.semantica;

/**
 * Variable almacenada en la tabla de simbolos
 *
 * @author César
 */
public final class EntradaTabla {

    private String identificador;
    private Tipo tipo;
    private boolean compartida;
    private int nivel;

    /**
     * Contruye una entrada de la tabla
     * @param identificador Identificador
     * @param tipo Tipo
     * @param compartida Compartida
     */
    public EntradaTabla(String identificador, Tipo tipo, boolean compartida) {
        this.identificador = identificador;
        this.tipo = tipo;
        this.compartida = compartida;
    }

    /**
     * Obtiene el identificador
     * @return Identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece el identificador
     * @param identificador Identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene el tipo
     * @return Tipo
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo
     * @param tipo Tipo
     */
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el nivel
     * @return Nivel
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * Establece el nivel
     * @param nivel Nivel
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    /**
     * Comprueba si la variable es global nivel==0
     * @return Es global
     */
    public boolean isGlobal() {
        return nivel == 0;
    }

}
