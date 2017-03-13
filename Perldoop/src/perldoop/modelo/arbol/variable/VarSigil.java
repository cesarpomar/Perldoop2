package perldoop.modelo.arbol.variable;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; variable : '$' '#' ID
 *
 * @author César Pomar
 */
public final class VarSigil extends Variable {

    private Terminal sigil;

    /**
     * Único contructor de la clase
     *
     * @param dolar Dolar
     * @param sigil Sigil
     * @param var Variable
     */
    public VarSigil(Terminal dolar, Terminal sigil, Terminal var) {
        super(dolar,var);
        setSigil(sigil);
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

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{contexto, sigil, var};
    }
}
