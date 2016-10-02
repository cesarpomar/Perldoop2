package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; expresion : expresion DOS_PUNTOS expresion
 *
 * @author César Pomar
 */
public final class ExpRango extends Expresion{
    
    private Expresion izquierda;
    private Terminal dosPuntos;
    private Expresion derecha;

    /**
     * Único contructor de la clase
     *
     * @param izquierda Izquierda
     * @param dosPuntos DosPuntos
     * @param derecha Derecha
     */
    public ExpRango(Expresion izquierda, Terminal dosPuntos, Expresion derecha) {
        setIzquierda(izquierda);
        setDosPuntos(dosPuntos);
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
     * Obtiene el dosPuntos
     *
     * @return DosPuntos
     */
    public Terminal getDosPuntos() {
        return dosPuntos;
    }

    /**
     * Establece el dosPuntos
     *
     * @param dosPuntos DosPuntos
     */
    public void setDosPuntos(Terminal dosPuntos) {
        dosPuntos.setPadre(this);
        this.dosPuntos = dosPuntos;
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
        return new Simbolo[]{izquierda, dosPuntos, derecha};
    }
    
}
