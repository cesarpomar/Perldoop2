package perldoop.modelo.preprocesador.hadoop;

import perldoop.modelo.lexico.Token;

/**
 * Etiquetas para definir el tipo de un bloque hadoop
 *
 * @author César Pomar
 */
public abstract class TagsHadoopApi {

    protected Token tipo;
    protected Token keyOut;
    protected Token valueOut;

    /**
     * Constructor por defecto
     *
     * @param tipo Tipo de API hadoop
     */
    public TagsHadoopApi(Token tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la etiqueta que define el tipo de API de hadoop
     *
     * @return Etiqueta del API
     */
    public Token getTipo() {
        return tipo;
    }

    /**
     * Establece la etiqueta que define el tipo de API de hadoop
     *
     * @param tipo Etiqueta del API
     */
    public void setTipo(Token tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la etiqueta de tipo para la clave de salida
     *
     * @return Etiqueta tipo la clave de salida
     */
    public final Token getKeyOut() {
        return keyOut;
    }

    /**
     * Establece la etiqueta de tipo para la clave de salida
     *
     * @param keyOut Etiqueta tipo
     */
    public final void setKeyOut(Token keyOut) {
        this.keyOut = keyOut;
    }

    /**
     * Obtiene la etiqueta de tipo para el valor de la salida
     *
     * @return Etiqueta tipo
     */
    public final Token getValueOut() {
        return valueOut;
    }

    /**
     * Establece la etiqueta de tipo para el valor de la salida
     *
     * @param valueOut Etiqueta tipo
     */
    public final void setValueOut(Token valueOut) {
        this.valueOut = valueOut;
    }

}
