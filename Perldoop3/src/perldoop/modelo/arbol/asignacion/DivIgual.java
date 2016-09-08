package perldoop.modelo.arbol.asignacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; asignacion : expresion DIV_IGUAL expresion
 *
 * @author César Pomar
 */
public final class DivIgual extends Asignacion {

    private Expresion izquierda;
    private Terminal divIgual;
    private Expresion derecha;

    /**
     * Único contructor de la clase
     *
     * @param izquierda Izquierda
     * @param divIgual DivIgual
     * @param derecha Derecha
     */
    public DivIgual(Expresion izquierda, Terminal divIgual, Expresion derecha) {
        setIzquierda(izquierda);
        setDivIgual(divIgual);
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
     * Obtiene el divIgual
     *
     * @return DivIgual
     */
    public Terminal getDivIgual() {
        return divIgual;
    }

    /**
     * Establece el divIgual
     *
     * @param divIgual DivIgual
     */
    public void setDivIgual(Terminal divIgual) {
        divIgual.setPadre(this);
        this.divIgual = divIgual;
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
        return new Simbolo[]{izquierda, divIgual, derecha};
    }

}
