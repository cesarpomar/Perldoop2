package perldoop.modelo.arbol.asignacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; asignacion : expresion MULTI_IGUAL expresion
 *
 * @author César Pomar
 */
public final class MultiIgual extends Asignacion {

    private Expresion izquierda;
    private Terminal multiIgual;
    private Expresion derecha;

    /**
     * Único contructor de la clase
     *
     * @param izquierda Izquierda
     * @param multiIgual MultiIgual
     * @param derecha Derecha
     */
    public MultiIgual(Expresion izquierda, Terminal multiIgual, Expresion derecha) {
        setIzquierda(izquierda);
        setMultiIgual(multiIgual);
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
     * Obtiene el multiIgual
     *
     * @return MultiIgual
     */
    public Terminal getMultiIgual() {
        return multiIgual;
    }

    /**
     * Establece el multiIgual
     *
     * @param multiIgual MultiIgual
     */
    public void setMultiIgual(Terminal multiIgual) {
        multiIgual.setPadre(this);
        this.multiIgual = multiIgual;
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
        return new Simbolo[]{izquierda, multiIgual, derecha};
    }

}
