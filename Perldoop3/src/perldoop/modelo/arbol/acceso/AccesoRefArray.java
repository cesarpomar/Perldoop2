package perldoop.modelo.arbol.acceso;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -> acceso : '@' expresion
 *
 * @author César Pomar
 */
public final class AccesoRefArray extends Acceso {

    private Terminal arroba;
    private Expresion expresion;

    /**
     * Único contructor de la clase
     *
     * @param arroba Arroba
     * @param expresion Expresión
     */
    public AccesoRefArray(Terminal arroba, Expresion expresion) {
        setArroba(arroba);
        setExpresion(expresion);
    }

    /**
     * Obtiene la arroba
     *
     * @return Arroba
     */
    public Terminal getArroba() {
        return arroba;
    }

    /**
     * Establece la arroba
     *
     * @param arroba Arroba
     */
    public void setArroba(Terminal arroba) {
        arroba.setPadre(padre);
        this.arroba = arroba;
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
        return new Simbolo[]{arroba, expresion};
    }
}
