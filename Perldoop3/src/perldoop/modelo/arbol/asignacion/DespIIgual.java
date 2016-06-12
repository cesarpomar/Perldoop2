package perldoop.modelo.arbol.asignacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -> asignacion : expresion DESP_I_IGUAL expresion
 *
 * @author César Pomar
 */
public final class DespIIgual extends Asignacion {

    private Expresion izquierda;
    private Terminal despIIgual;
    private Expresion derecha;

    /**
     * Único contructor de la clase
     *
     * @param izquierda Izquierda
     * @param despIIgual DespIIgual
     * @param derecha Derecha
     */
    public DespIIgual(Expresion izquierda, Terminal despIIgual, Expresion derecha) {
        setIzquierda(izquierda);
        setDespIIgual(despIIgual);
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
     * Obtiene el despIIgual
     *
     * @return DespIIgual
     */
    public Terminal getDespIIgual() {
        return despIIgual;
    }

    /**
     * Establece el despIIgual
     *
     * @param despIIgual DespIIgual
     */
    public void setDespIIgual(Terminal despIIgual) {
        despIIgual.setPadre(this);
        this.despIIgual = despIIgual;
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
        return new Simbolo[]{izquierda, despIIgual, derecha};
    }

}
