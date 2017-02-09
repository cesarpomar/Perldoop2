package perldoop.modelo.preprocesador;

import java.util.ArrayList;
import java.util.List;
import perldoop.modelo.lexico.Token;

/**
 * Etiquetas para predefinir el tipo de variables
 *
 * @author César Pomar
 */
public final class TagsPredeclaracion implements Tags {

    private List<Token> variables;
    private TagsTipo tipo;

    /**
     * Constructor por defecto
     */
    public TagsPredeclaracion() {
        variables = new ArrayList<>(5);
        tipo = new TagsTipo();
    }

    /**
     * Añade una variable
     *
     * @param variable Variable
     */
    public void addVariable(Token variable) {
        variables.add(variable);
    }

    /**
     * Obtiene las varibles
     *
     * @return Variables
     */
    public List<Token> getVariables() {
        return variables;
    }

    /**
     * Obtiene las etiquetas de tipo
     *
     * @return Etiquetas de tipo
     */
    public TagsTipo getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "EtiquetasPredeclaracion( variables(" + variables + "), " + tipo + ')';
    }

    @Override
    public int getLinea() {
        return variables.get(0).getLinea();
    }
}
