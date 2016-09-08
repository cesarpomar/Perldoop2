package perldoop.modelo.arbol.acceso;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; acceso : '$' expresion
 *
 * @author César Pomar
 */
public final class AccesoRefEscalar extends Acceso {

    private Terminal dolar;

    /**
     * Único contructor de la clase
     *
     * @param dolar Dolar
     * @param expresion Expresión
     */
    public AccesoRefEscalar(Terminal dolar, Expresion expresion) {
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

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{dolar, expresion};
    }

}
