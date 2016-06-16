package perldoop.modelo.arbol.variable;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;

/**
 * Clase abtracta que representa todas las reduciones de variable
 *
 * @author César Pomar
 */
public abstract class Variable extends Simbolo {

    protected Terminal var;

    /**
     * Único contructor de la clase
     *
     * @param var Var
     */
    public Variable(Terminal var) {
        setVar(var);
    }

    /**
     * Obtiene la variable
     *
     * @return Variable
     */
    public final Terminal getVar() {
        return var;
    }

    /**
     * Establece la variable
     *
     * @param var Variable
     */
    public final void setVar(Terminal var) {
        var.setPadre(this);
        this.var = var;
    }
}
