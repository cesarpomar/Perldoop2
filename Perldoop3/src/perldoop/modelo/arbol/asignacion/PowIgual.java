package perldoop.modelo.arbol.asignacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -> asignacion : expresion POW_IGUAL expresion
 *
 * @author César Pomar
 */
public final class PowIgual extends Asignacion {

    private Expresion izquierda;
    private Terminal powIgual;
    private Expresion derecha;

    /**
     * Único contructor de la clase
     *
     * @param izquierda Izquierda
     * @param powIgual PowIgual
     * @param derecha Derecha
     */
    public PowIgual(Expresion izquierda, Terminal powIgual, Expresion derecha) {
        setIzquierda(izquierda);
        setPowIgual(powIgual);
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
     * Obtiene el powIgual
     *
     * @return PowIgual
     */
    public Terminal getPowIgual() {
        return powIgual;
    }

    /**
     * Establece el powIgual
     *
     * @param powIgual PowIgual
     */
    public void setPowIgual(Terminal powIgual) {
        powIgual.setPadre(this);
        this.powIgual = powIgual;
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
        return new Simbolo[]{izquierda, powIgual, derecha};
    }

}
