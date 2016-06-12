package perldoop.modelo.arbol.asignacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -> asignacion : expresion DESP_D_IGUAL expresion
 *
 * @author César Pomar
 */
public final class DespDIgual extends Asignacion {

    private Expresion izquierda;
    private Terminal despDIgual;
    private Expresion derecha;

    /**
     * Único contructor de la clase
     *
     * @param izquierda Izquierda
     * @param despDIgual DespDIgual
     * @param derecha Derecha
     */
    public DespDIgual(Expresion izquierda, Terminal despDIgual, Expresion derecha) {
        setIzquierda(izquierda);
        setDespDIgual(despDIgual);
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
     * Obtiene el despDIgual
     *
     * @return DespDIgual
     */
    public Terminal getDespDIgual() {
        return despDIgual;
    }

    /**
     * Establece el despDIgual
     *
     * @param despDIgual DespDIgual
     */
    public void setDespDIgual(Terminal despDIgual) {
        despDIgual.setPadre(this);
        this.despDIgual = despDIgual;
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
        return new Simbolo[]{izquierda, despDIgual, derecha};
    }

}
