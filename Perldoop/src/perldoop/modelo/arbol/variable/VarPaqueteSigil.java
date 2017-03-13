package perldoop.modelo.arbol.variable;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.paquete.Paquetes;

/**
 * Clase que representa la reduccion -&gt; variable : '$' '#' paqueteVar ID
 *
 * @author César Pomar
 */
public final class VarPaqueteSigil extends Variable {

    private Paquetes paquetes;
    private Terminal sigil;

    /**
     * Único contructor de la clase
     *
     * @param dolar Dolar
     * @param sigil Sigil
     * @param paquetes paquetes
     * @param var Variable
     */
    public VarPaqueteSigil(Terminal dolar, Terminal sigil, Paquetes paquetes, Terminal var) {
        super(dolar, var);
        setSigil(sigil);
        setPaquetes(paquetes);
    }

    /**
     * Obtiene el sigil
     *
     * @return Sigil
     */
    public Terminal getSigil() {
        return sigil;
    }

    /**
     * Establece el sigil
     *
     * @param sigil Sigil
     */
    public void setSigil(Terminal sigil) {
        sigil.setPadre(this);
        this.sigil = sigil;
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
        return new Simbolo[]{contexto, sigil, paquetes, var};
    }

}
