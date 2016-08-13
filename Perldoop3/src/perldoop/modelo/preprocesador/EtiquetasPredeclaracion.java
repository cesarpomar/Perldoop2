package perldoop.modelo.preprocesador;

import java.util.ArrayList;
import java.util.List;
import perldoop.modelo.lexico.Token;

/**
 * Etiques para predefinir el tipo de variables
 *
 * @author César Pomar
 */
public final class EtiquetasPredeclaracion implements Etiquetas {

    private List<Token> variables;
    private EtiquetasTipo tipo;

    /**
     * Constructor por defecto
     */
    public EtiquetasPredeclaracion() {
        variables = new ArrayList<>(5);
        tipo = new EtiquetasTipo();
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
    public EtiquetasTipo getTipo() {
        return tipo;
    }

}
