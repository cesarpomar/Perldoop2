package perldoop.modelo.arbol.variable;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -> variable : MY VAR
 *
 * @author César Pomar
 */
public final class VarMy extends Variable {

    private Terminal my;
    private Terminal var;

    /**
     * Único contructor de la clase
     *
     * @param my My
     * @param var Var
     */
    public VarMy(Terminal my, Terminal var) {
        setMy(my);
        setVar(var);
    }

    /**
     * Obtiene el my
     *
     * @return My
     */
    public Terminal getMy() {
        return my;
    }

    /**
     * Establece el my
     *
     * @param my My
     */
    public void setMy(Terminal my) {
        my.setPadre(this);
        this.my = my;
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
        return new Simbolo[]{my, var};
    }
}
