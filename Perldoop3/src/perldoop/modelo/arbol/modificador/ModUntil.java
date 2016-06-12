package perldoop.modelo.arbol.modificador;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -> modificador : UNTIL espresion
 *
 * @author César Pomar
 */
public final class ModUntil extends Modificador {

    private Terminal untilT;
    private Expresion expresion;

    /**
     * Único contructor de la clase
     *
     * @param until Until
     * @param expresion Expresión
     */
    public ModUntil(Terminal until, Expresion expresion) {
        setUntilT(until);
        setExpresion(expresion);
    }

    /**
     * Obtiene el until
     *
     * @return Until
     */
    public Terminal getUntilT() {
        return untilT;
    }

    /**
     * Establece el until
     *
     * @param untilT Until
     */
    public void setUntilT(Terminal untilT) {
        untilT.setPadre(this);
        this.untilT = untilT;
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
        return new Simbolo[]{untilT, expresion};
    }
}
