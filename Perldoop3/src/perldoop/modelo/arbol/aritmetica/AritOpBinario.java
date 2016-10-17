package perldoop.modelo.arbol.aritmetica;

import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.expresion.Expresion;


/**
 * Clase abtracta que representa todas las reduciones de aritmetica con dos expresiones
 *
 * @author César Pomar
 */
public abstract class AritOpBinario extends Aritmetica {

    protected Expresion izquierda;
    protected Expresion derecha;
    
        /**
     * Único contructor de la clase
     *
     * @param izquierda Simbolo izquierdo
     * @param operador Operador
     * @param derecha Simbolo derecho
     */
    public AritOpBinario(Expresion izquierda, Terminal operador, Expresion derecha) {
        super(operador);
        setIzquierda(izquierda);
        setDerecha(derecha);
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
