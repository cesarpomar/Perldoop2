package perldoop.modelo.arbol.asignacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; asignacion : expresion CONCAT_IGUAL expresion
 *
 * @author César Pomar
 */
public final class ConcatIgual extends Asignacion {

    private Expresion izquierda;
    private Terminal concatIgual;
    private Expresion derecha;

    /**
     * Único contructor de la clase
     *
     * @param izquierda Izquierda
     * @param concatIgual ConcatIgual
     * @param derecha Derecha
     */
    public ConcatIgual(Expresion izquierda, Terminal concatIgual, Expresion derecha) {
        setIzquierda(izquierda);
        setConcatIgual(concatIgual);
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
     * Obtiene el concatIgual
     *
     * @return ConcatIgual
     */
    public Terminal getConcatIgual() {
        return concatIgual;
    }

    /**
     * Establece el concatIgual
     *
     * @param concatIgual ConcatIgual
     */
    public void setConcatIgual(Terminal concatIgual) {
        concatIgual.setPadre(this);
        this.concatIgual = concatIgual;
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
        return new Simbolo[]{izquierda, concatIgual, derecha};
    }

}
