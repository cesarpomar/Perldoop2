package perldoop.modelo.arbol.variable;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; <br>variable : MY '$' ID<br>
 * MY '@' ID<br>
 * MY '%' ID
 *
 * @author César Pomar
 */
public final class VarMy extends Variable {

    private Terminal my;

    /**
     * Único contructor de la clase
     *
     * @param my My
     * @param contexto Contexto
     * @param var Var
     */
    public VarMy(Terminal my, Terminal contexto, Terminal var) {
        super(contexto, var);
        setMy(my);
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

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{my, contexto, var};
    }
}
