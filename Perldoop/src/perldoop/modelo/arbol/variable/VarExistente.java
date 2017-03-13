package perldoop.modelo.arbol.variable;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; <br>variable : '$' ID<br>
 * '@' ID<br>
 * '%' ID
 *
 * @author César Pomar
 */
public final class VarExistente extends Variable {

    /**
     * Único contructor de la clase
     *
     * @param contexto Contexto
     * @param var Variable
     */
    public VarExistente(Terminal contexto, Terminal var) {
        super(contexto, var);
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
