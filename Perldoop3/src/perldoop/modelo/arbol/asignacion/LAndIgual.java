package perldoop.modelo.arbol.asignacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -> asignacion : expresion LAND_IGUAL expresion
 *
 * @author César Pomar
 */
public final class LAndIgual extends Asignacion {

    private Expresion izquierda;
    private Terminal landIgual;
    private Expresion derecha;

    /**
     * Único contructor de la clase
     *
     * @param izquierda Izquierda
     * @param lAndIgual LAndIgual
     * @param derecha Derecha
     */
    public LAndIgual(Expresion izquierda, Terminal lAndIgual, Expresion derecha) {
        setIzquierda(izquierda);
        setLAndIgual(lAndIgual);
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
     * Obtiene el lAndIgual
     *
     * @return LAndIgual
     */
    public Terminal getLandIgual() {
        return landIgual;
    }

    /**
     * Establece el lAndIgual
     *
     * @param landIgual LAndIgual
     */
    public void setLAndIgual(Terminal landIgual) {
        landIgual.setPadre(this);
        this.landIgual = landIgual;
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
        return new Simbolo[]{izquierda, landIgual, derecha};
    }

}
