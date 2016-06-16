package perldoop.modelo.arbol.acceso;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -> acceso : '$' expresion
 *
 * @author César Pomar
 */
public final class AccesoSigil extends Acceso {

    private Terminal dolar;
    private Terminal sigil;

    /**
     * Único contructor de la clase
     *
     * @param dolar Dolar
     * @param sigil Sigil
     * @param expresion Expresión
     */
    public AccesoSigil(Terminal dolar, Terminal sigil, Expresion expresion) {
        super(expresion);
        setDolar(dolar);
    }

    /**
     * Obtiene el dolar
     *
     * @return Dolar
     */
    public Terminal getDolar() {
        return dolar;
    }

    /**
     * Establece el dolar
     *
     * @param dolar Dolar
     */
    public void setDolar(Terminal dolar) {
        dolar.setPadre(padre);
        this.dolar = dolar;
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
        return new Simbolo[]{dolar, sigil, expresion};
    }

}
