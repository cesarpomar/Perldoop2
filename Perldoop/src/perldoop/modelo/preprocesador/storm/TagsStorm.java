package perldoop.modelo.preprocesador.storm;

import perldoop.modelo.lexico.Token;
import perldoop.modelo.preprocesador.TagsBloque;

/**
 * Etiquetas para definir el tipo de un bloque storm
 *
 * @author César Pomar
 */
public final class TagsStorm extends TagsBloque {

    private Token input;
    private Token output;

    /**
     * Constructor por defecto
     *
     * @param etiqueta Etiqueta de bloque
     */
    public TagsStorm(Token etiqueta) {
        super(etiqueta);
    }

    /**
     * Obtiene el token Input
     *
     * @return Token Input
     */
    public Token getInput() {
        return input;
    }

    /**
     * Establece el token Input
     *
     * @param input Token Input
     */
    public void setInput(Token input) {
        this.input = input;
    }

    /**
     * Obtiene el token Output
     *
     * @return Token Output
     */
    public Token getOutput() {
        return output;
    }

    /**
     * Establece el token Output
     *
     * @param output Token Output
     */
    public void setOutput(Token output) {
        this.output = output;
    }

}
