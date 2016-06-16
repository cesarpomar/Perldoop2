package perldoop.modelo.arbol.variable;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -> variable : OUR VAR
 *
 * @author César Pomar
 */
public final class VarOur extends Variable {

    private Terminal our;

    /**
     * Único contructor de la clase
     *
     * @param our Our
     * @param var Var
     */
    public VarOur(Terminal our, Terminal var) {
        super(var);
        setOur(our);
    }

    /**
     * Obtiene el our
     *
     * @return Our
     */
    public Terminal getOur() {
        return our;
    }

    /**
     * Establece el our
     *
     * @param our Our
     */
    public void setOur(Terminal our) {
        our.setPadre(this);
        this.our = our;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{our, var};
    }
}
