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
public final class AccesoRefEscalar extends Acceso {

    private Terminal dolar;
    private Expresion expresion;

    /**
     * Único contructor de la clase
     *
     * @param dolar Dolar
     * @param expresion Expresión
     */
    public AccesoRefEscalar(Terminal dolar, Expresion expresion) {
        setDolar(dolar);
        setExpresion(expresion);
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
     * Obtiene la expresión
     *
     * @return Expresión
     */
    public Expresion getExpresion() {
        return expresion;
    }

    /**
     * Establece la expresión
     *
     * @param expresion Expresión
     */
    public void setExpresion(Expresion expresion) {
        expresion.setPadre(this);
        this.expresion = expresion;
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
