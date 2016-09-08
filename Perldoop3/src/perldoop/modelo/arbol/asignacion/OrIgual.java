package perldoop.modelo.arbol.asignacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; asignacion : expresion OR_IGUAL expresion
 *
 * @author César Pomar
 */
public final class OrIgual extends Asignacion {

    private Expresion izquierda;
    private Terminal orIgual;
    private Expresion derecha;

    /**
     * Único contructor de la clase
     *
     * @param izquierda Izquierda
     * @param orIgual OrIgual
     * @param derecha Derecha
     */
    public OrIgual(Expresion izquierda, Terminal orIgual, Expresion derecha) {
        setIzquierda(izquierda);
        setOrIgual(orIgual);
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
     * Obtiene el orIgual
     *
     * @return OrIgual
     */
    public Terminal getOrIgual() {
        return orIgual;
    }

    /**
     * Establece el orIgual
     *
     * @param orIgual OrIgual
     */
    public void setOrIgual(Terminal orIgual) {
        orIgual.setPadre(this);
        this.orIgual = orIgual;
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
        return new Simbolo[]{izquierda, orIgual, derecha};
    }

}
