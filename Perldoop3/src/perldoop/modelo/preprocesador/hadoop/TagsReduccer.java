package perldoop.modelo.preprocesador.hadoop;

import perldoop.modelo.lexico.Token;

/**
 * Etiquetas para definir el tipo de un bloque como reducer
 *
 * @author César Pomar
 */
public final class TagsReduccer extends TagsHadoopApi {

    private Token varKey;
    private Token varValue;
    private Token keyIn;
    private Token valueIn;

    /**
     * Constructor por defecto
     *
     * @param etiqueta Etiqueta de bloque
     */
    public TagsReduccer(Token etiqueta) {
        super(etiqueta);
    }

    /**
     * Obtiene la variable clave
     *
     * @return Variable clave
     */
    public Token getVarKey() {
        return varKey;
    }

    /**
     * Establece la variable clave
     *
     * @param varKey Variable clave
     */
    public void setVarKey(Token varKey) {
        this.varKey = varKey;
    }

    /**
     * Obtiene la variable valor
     *
     * @return Variable valor
     */
    public Token getVarValue() {
        return varValue;
    }

    /**
     * Establece la variable valor
     *
     * @param varValue Variable valor
     */
    public void setVarValue(Token varValue) {
        this.varValue = varValue;
    }

    /**
     * Obtiene la etiqueta de tipo para la clave de entrada
     *
     * @return Etiqueta tipo
     */
    public Token getKeyIn() {
        return keyIn;
    }

    /**
     * Establece la etiqueta de tipo para la clave de entrada
     *
     * @param keyIn Etiqueta tipo
     */
    public void setKeyIn(Token keyIn) {
        this.keyIn = keyIn;
    }

    /**
     * Obtiene la etiqueta de tipo para el valor de la entrada
     *
     * @return Etiqueta tipo
     */
    public Token getValueIn() {
        return valueIn;
    }

    /**
     * Establece la etiqueta de tipo para el valor de la entrada
     *
     * @param valueIn Etiqueta tipo
     */
    public void setValueIn(Token valueIn) {
        this.valueIn = valueIn;
    }

}
