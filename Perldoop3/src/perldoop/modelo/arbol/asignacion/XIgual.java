package perldoop.modelo.arbol.asignacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; asignacion : expresion X_IGUAL expresion
 *
 * @author César Pomar
 */
public final class XIgual extends Asignacion {

    private Expresion izquierda;
    private Terminal xIgual;
    private Expresion derecha;

    /**
     * Único contructor de la clase
     *
     * @param izquierda Izquierda
     * @param xIgual XIgual
     * @param derecha Derecha
     */
    public XIgual(Expresion izquierda, Terminal xIgual, Expresion derecha) {
        setIzquierda(izquierda);
        setXIgual(xIgual);
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
     * Obtiene el xIgual
     *
     * @return XIgual
     */
    public Terminal getXIgual() {
        return xIgual;
    }

    /**
     * Establece el xIgual
     *
     * @param xIgual XIgual
     */
    public void setXIgual(Terminal xIgual) {
        xIgual.setPadre(this);
        this.xIgual = xIgual;
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
        return new Simbolo[]{izquierda, xIgual, derecha};
    }

}
