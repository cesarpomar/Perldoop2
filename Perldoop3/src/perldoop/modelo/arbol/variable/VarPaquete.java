package perldoop.modelo.arbol.variable;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.paquete.Paquete;

/**
 * Clase que representa la reduccion -> variable : paquete VAR
 *
 * @author César Pomar
 */
public final class VarPaquete extends Variable {

    private Paquete paquete;
    private Terminal var;

    /**
     * Único contructor de la clase
     *
     * @param paquete Paquete
     * @param var Variable
     */
    public VarPaquete(Paquete paquete, Terminal var) {
        setVar(var);
        setPaquete(paquete);
    }

    /**
     * Obtiene el paquete
     *
     * @return Paquete
     */
    public Paquete getPaquete() {
        return paquete;
    }

    /**
     * Establece el paquete
     *
     * @param paquete Paquete
     */
    public void setPaquete(Paquete paquete) {
        paquete.setPadre(this);
        this.paquete = paquete;
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
        return new Simbolo[]{paquete, var};
    }
}
