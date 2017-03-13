package perldoop.modelo.arbol.comparacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase abtracta que representa todas las reduciones de comparacion
 *
 * @author César Pomar
 */
public abstract class Comparacion extends Simbolo {

    protected Expresion izquierda;
    protected Terminal operador;
    protected Expresion derecha;

    /**
     * Único contructor de la clase
     *
     * @param izquierda Simbolo izquierdo
     * @param operador Operador
     * @param derecha Simbolo derecho
     */
    public Comparacion(Expresion izquierda, Terminal operador, Expresion derecha) {
        setIzquierda(izquierda);
        setOperador(operador);
        setDerecha(derecha);
    }

    /**
     * Obtiene el operador
     *
     * @return Operador
     */
    public final Terminal getOperador() {
        return operador;
    }

    /**
     * Establece el operador
     *
     * @param operador Operador
     */
    public final void setOperador(Terminal operador) {
        operador.setPadre(this);
        this.operador = operador;
    }

    /**
     * Obtiene el Simbolo izquierdo
     *
     * @return Simbolo izquierdo
     */
    public final Expresion getIzquierda() {
        return izquierda;
    }

    /**
     * Establece el simbolo izquierdo
     *
     * @param izquierda Simbolo izquierdo
     */
    public final void setIzquierda(Expresion izquierda) {
        izquierda.setPadre(this);
        this.izquierda = izquierda;
    }

    /**
     * Establece el simbolo derecho
     *
     * @return Simbolo derecho
     */
    public final Expresion getDerecha() {
        return derecha;
    }

    /**
     * Obtiene el simbolo derecho
     *
     * @param derecha Simbolo derecho
     */
    public final void setDerecha(Expresion derecha) {
        derecha.setPadre(this);
        this.derecha = derecha;
    }
}
