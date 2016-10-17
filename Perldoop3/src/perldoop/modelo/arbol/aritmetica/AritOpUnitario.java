package perldoop.modelo.arbol.aritmetica;

import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase abtracta que representa todas las reduciones de aritmetica una expresion
 *
 * @author César Pomar
 */
public abstract class AritOpUnitario extends Aritmetica {

    protected Expresion expresion;

    /**
     * Único contructor de la clase
     *
     * @param operador Operador
     * @param expresion Expresion
     */
    public AritOpUnitario(Terminal operador, Expresion expresion) {
        super(operador);
        setExpresion(expresion);
    }

    /**
     * Obtiene la expresion
     *
     * @return Expresion
     */
    public final Expresion getExpresion() {
        return expresion;
    }

    /**
     * Establece la expresion
     *
     * @param expresion Expresion
     */
    public final void setExpresion(Expresion expresion) {
        expresion.setPadre(this);
        this.expresion = expresion;
    }

}
