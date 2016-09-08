package perldoop.modelo.arbol.asignacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; asignacion : expresion LOR_IGUAL expresion
 *
 * @author César Pomar
 */
public final class LOrIgual extends Asignacion {

    private Expresion izquierda;
    private Terminal lOrIgual;
    private Expresion derecha;

    /**
     * Único contructor de la clase
     *
     * @param izquierda Izquierda
     * @param lOrIgual LorIgual
     * @param derecha Derecha
     */
    public LOrIgual(Expresion izquierda, Terminal lOrIgual, Expresion derecha) {
        setIzquierda(izquierda);
        setlOrIgual(lOrIgual);
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
     * Obtiene el lOrIgual
     *
     * @return LOrIgual
     */
    public Terminal getlOrIgual() {
        return lOrIgual;
    }

    /**
     * Establece el lOrIgual
     *
     * @param lOrIgual LOrIgual
     */
    public void setlOrIgual(Terminal lOrIgual) {
        lOrIgual.setPadre(this);
        this.lOrIgual = lOrIgual;
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
        return new Simbolo[]{izquierda, lOrIgual, derecha};
    }

}
