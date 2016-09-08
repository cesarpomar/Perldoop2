package perldoop.modelo.arbol.asignacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; asignacion : expresion MAS_IGUAL expresion
 *
 * @author César Pomar
 */
public final class MasIgual extends Asignacion {

    private Expresion izquierda;
    private Terminal masIgual;
    private Expresion derecha;

    /**
     * Único contructor de la clase
     *
     * @param izquierda Izquierda
     * @param masIgual MasIgual
     * @param derecha Derecha
     */
    public MasIgual(Expresion izquierda, Terminal masIgual, Expresion derecha) {
        setIzquierda(izquierda);
        setMasIgual(masIgual);
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
     * Obtiene el masIgual
     *
     * @return MasIgual
     */
    public Terminal getMasIgual() {
        return masIgual;
    }

    /**
     * Establece el masIgual
     *
     * @param masIgual MasIgual
     */
    public void setMasIgual(Terminal masIgual) {
        masIgual.setPadre(this);
        this.masIgual = masIgual;
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
        return new Simbolo[]{izquierda, masIgual, derecha};
    }

}
