package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.variable.Variable;

/**
 * Clase que representa la reduccion -&gt; expresion : variable
 *
 * @author César Pomar
 */
public final class ExpVariable extends Expresion {

    private Variable variable;

    /**
     * Único contructor de la clase
     *
     * @param variable Variable
     */
    public ExpVariable(Variable variable) {
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
     * @param variable variable
     */
    public void setVariable(Variable variable) {
        variable.setPadre(this);
        this.variable = variable;
    }

    @Override
    public Simbolo getValor() {
        return variable;
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
