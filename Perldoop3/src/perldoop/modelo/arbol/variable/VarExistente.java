package perldoop.modelo.arbol.variable;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -> variable : VAR
 *
 * @author César Pomar
 */
public final class VarExistente extends Variable {

    private Terminal var;

    /**
     * Único contructor de la clase
     *
     * @param var Variable
     */
    public VarExistente(Terminal var) {
        setVar(var);
    }

    /**
     * Obtiene la variable
     *
     * @return Variable
     */
    public Terminal getVar() {
        return var;
    }

    /**
     * Establece la variable
     *
     * @param var Variable
     */
    public void setVar(Terminal var) {
        var.setPadre(this);
        this.var = var;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{var};
    }

}
