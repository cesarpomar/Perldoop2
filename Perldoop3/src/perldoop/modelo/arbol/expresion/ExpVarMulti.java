package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.varmulti.VarMulti;

/**
 * Clase que representa la reduccion -&gt; expresion : varMulti
 *
 * @author César Pomar
 */
public class ExpVarMulti extends Expresion {

    private VarMulti variables;

    /**
     * Único contructor de la clase
     *
     * @param variables Variables
     */
    public ExpVarMulti(VarMulti variables) {
        setVariables(variables);
    }

    /**
     * Obtiene las variables
     *
     * @return Variables
     */
    public final VarMulti getVariables() {
        return variables;
    }

    /**
     * Establece las variables
     *
     * @param variables variables
     */
    public final void setVariables(VarMulti variables) {
        variables.setPadre(this);
        this.variables = variables;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{variables};
    }
}
