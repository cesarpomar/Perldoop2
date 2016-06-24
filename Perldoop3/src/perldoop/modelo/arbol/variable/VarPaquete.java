package perldoop.modelo.arbol.variable;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.paquete.Paquetes;

/**
 * Clase que representa la reduccion -> <br>variable : '$' paqueteVar ID<br>
 * '@' paqueteVar ID<br>
 * '%' paqueteVar ID
 *
 * @author César Pomar
 */
public final class VarPaquete extends Variable {

    private Paquetes paquetes;

    /**
     * Único contructor de la clase
     *
     * @param contexto Contexto
     * @param paquetes paquetes
     * @param var Variable
     */
    public VarPaquete(Terminal contexto, Paquetes paquetes, Terminal var) {
        super(contexto, var);
        setPaquetes(paquetes);
    }

    /**
     * Obtiene los paquetes
     *
     * @return Paquetes
     */
    public Paquetes getPaquetes() {
        return paquetes;
    }

    /**
     * Establece los paquetes
     *
     * @param paquetes Paquetes
     */
    public void setPaquetes(Paquetes paquetes) {
        paquetes.setPadre(this);
        this.paquetes = paquetes;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{contexto, paquetes, var};
    }
}
