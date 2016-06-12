package perldoop.modelo.arbol.asignacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -> asignacion : expresion AND_IGUAL expresion
 *
 * @author César Pomar
 */
public final class AndIgual extends Asignacion {

    private Expresion izquierda;
    private Terminal andIgual;
    private Expresion derecha;

    /**
     * Único contructor de la clase
     *
     * @param izquierda Izquierda
     * @param andIgual AndIgual
     * @param derecha Derecha
     */
    public AndIgual(Expresion izquierda, Terminal andIgual, Expresion derecha) {
        setIzquierda(izquierda);
        setAndIgual(andIgual);
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
     * Obtiene el andIgual
     *
     * @return AndIgual
     */
    public Terminal getAndIgual() {
        return andIgual;
    }

    /**
     * Establece el andIgual
     *
     * @param andIgual AndIgual
     */
    public void setAndIgual(Terminal andIgual) {
        andIgual.setPadre(this);
        this.andIgual = andIgual;
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
        return new Simbolo[]{izquierda, andIgual, derecha};
    }

}
