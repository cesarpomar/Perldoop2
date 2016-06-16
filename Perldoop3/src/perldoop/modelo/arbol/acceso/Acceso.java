package perldoop.modelo.arbol.acceso;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase abtracta que representa todas las reduciones de acceso
 *
 * @author César Pomar
 */
public abstract class Acceso extends Simbolo {

    protected Expresion expresion;

    /**
     * Único contructor de la clase
     *
     * @param expresion Expresión
     */
    public Acceso(Expresion expresion) {
        this.expresion = expresion;
    }

    /**
     * Obtiene la expresión
     *
     * @return Expresión
     */
    public final Expresion getExpresion() {
        return expresion;
    }

    /**
     * Establece la expresión
     *
     * @param expresion Expresión
     */
    public final void setExpresion(Expresion expresion) {
        expresion.setPadre(this);
        this.expresion = expresion;
    }
}
