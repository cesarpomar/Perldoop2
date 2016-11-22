package perldoop.modelo.preprocesador.hadoop;

import perldoop.modelo.lexico.Token;

/**
 * Etiquetas para definir el tipo de un bloque hadoop reduccer
 *
 * @author César Pomar
 */
public final class TagsHadoopReduccer extends TagsHadoopApi {

    private Token keyIn;
    private Token valueIn;
    private Token key;
    private Token value;

    /**
     * Constructor por defecto
     *
     * @param tipo Tipo de API hadoop
     */
    public TagsHadoopReduccer(Token tipo) {
        super(tipo);
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

    /**
     * Obtiene la variable clave
     *
     * @return Variable clave
     */
    public Token getKey() {
        return key;
    }

    /**
     * Establece la variable clave
     *
     * @param key Variable clave
     */
    public void setKey(Token key) {
        this.key = key;
    }

    /**
     * Obtiene la variable valor
     *
     * @return Variable valor
     */
    public Token getValue() {
        return value;
    }

    /**
     * Establece la variable valor
     *
     * @param value Variable valor
     */
    public void setValue(Token value) {
        this.value = value;
    }

}
