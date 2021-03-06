package perldoop.modelo.lexico;

/**
 * Token del analizador lexico
 *
 * @author César Pomar
 */
public class Token {

    private String valor;
    private int tipo;
    private int linea;
    private int columna;
    private int posicion;
    private boolean etiqueta;

    /**
     * Construye un token vacio
     */
    public Token() {
        valor = "";
    }

    /**
     * Construye un token vcon unvalor
     *
     * @param valor Valor
     */
    public Token(String valor) {
        this.valor = valor;
    }

    /**
     * Construye un token
     *
     * @param valor Valor
     * @param tipo Tipo
     * @param linea Linea
     * @param columna Columna
     * @param posicion Posición
     * @param etiqueta Etiqueta
     */
    public Token(String valor, int tipo, int linea, int columna, int posicion, boolean etiqueta) {
        this.valor = valor;
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
        this.posicion = posicion;
        this.etiqueta = etiqueta;
    }

    /**
     * Obtiene el valor
     *
     * @return Valor
     */
    public final String getValor() {
        return valor;
    }

    /**
     * Establece el valor
     *
     * @param valor Valor
     */
    public final void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * Obtiene el tipo
     *
     * @return Tipo
     */
    public final int getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo
     *
     * @param tipo Tipo
     */
    public final void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la linea
     *
     * @return Linea
     */
    public final int getLinea() {
        return linea;
    }

    /**
     * Establece la linea
     *
     * @param linea Linea
     */
    public final void setLinea(int linea) {
        this.linea = linea;
    }

    /**
     * Obtiene la columna
     *
     * @return Columna
     */
    public final int getColumna() {
        return columna;
    }

    /**
     * Establece la columna
     *
     * @param columna Columna
     */
    public final void setColumna(int columna) {
        this.columna = columna;
    }

    /**
     * Obtiene la posición
     *
     * @return Posición
     */
    public int getPosicion() {
        return posicion;
    }

    /**
     * Establece la posicón
     *
     * @param posicion Posición
     */
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    /**
     * Obtiene si es una etiqueta
     *
     * @return Es etiqueta
     */
    public final boolean isEtiqueta() {
        return etiqueta;
    }

    /**
     * Establece si es una etiqueta
     *
     * @param etiqueta Es etiqueta
     */
    public final void setEtiqueta(boolean etiqueta) {
        this.etiqueta = etiqueta;
    }

    @Override
    public String toString() {
        return valor;
    }

}
