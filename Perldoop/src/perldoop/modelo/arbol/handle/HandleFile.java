package perldoop.modelo.arbol.handle;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.variable.Variable;

/**
 * Clase que representa la reduccion -&gt; handle : STDOUT
 *
 * @author César Pomar
 */
public final class HandleFile extends Handle {

    private Variable variable;

    /**
     * Constructor unico de la clase
     *
     * @param variable Variable
     */
    public HandleFile(Variable variable) {
        setVariable(variable);
    }

    /**
     * Obtiene la variable
     *
     * @return Variable
     */
    public Variable getVariable() {
        return variable;
    }

    /**
     * Establece la variable
     *
     * @param variable Variable
     */
    public void setVariable(Variable variable) {
        variable.setPadre(this);
        this.variable = variable;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{variable};
    }

}
