package perldoop.modelo.arbol.asignacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; asignacion : expresion XOR_IGUAL expresion
 *
 * @author César Pomar
 */
public final class XorIgual extends Asignacion {

    private Expresion izquierda;
    private Terminal xorIgual;
    private Expresion derecha;

    /**
     * Único contructor de la clase
     *
     * @param izquierda Izquierda
     * @param xorIgual XorIgual
     * @param derecha Derecha
     */
    public XorIgual(Expresion izquierda, Terminal xorIgual, Expresion derecha) {
        setIzquierda(izquierda);
        setXorIgual(xorIgual);
        setDerecha(derecha);
    }

    /**
     * Obtiene el Simbolo izquierdo
     *
     * @return Simbolo izquierdo
     */
    public Expresion getIzquierda() {
        return izquierda;
    }

    /**
     * Establece el simbolo izquierdo
     *
     * @param izquierda Simbolo izquierdo
     */
    public void setIzquierda(Expresion izquierda) {
        izquierda.setPadre(this);
        this.izquierda = izquierda;
    }

    /**
     * Obtiene el xorIgual
     *
     * @return XorIgual
     */
    public Terminal getXorIgual() {
        return xorIgual;
    }

    /**
     * Establece el xorIgual
     *
     * @param xorIgual XorIgual
     */
    public void setXorIgual(Terminal xorIgual) {
        xorIgual.setPadre(this);
        this.xorIgual = xorIgual;
    }

    /**
     * Establece el simbolo derecho
     *
     * @return Simbolo derecho
     */
    public Expresion getDerecha() {
        return derecha;
    }

    /**
     * Obtiene el simbolo derecho
     *
     * @param derecha Simbolo derecho
     */
    public void setDerecha(Expresion derecha) {
        derecha.setPadre(this);
        this.derecha = derecha;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{izquierda, xorIgual, derecha};
    }

}
