package perldoop.modelo.arbol.asignacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; asignacion : expresion MENOS_IGUAL expresion
 *
 * @author César Pomar
 */
public final class MenosIgual extends Asignacion {

    private Expresion izquierda;
    private Terminal menosIgual;
    private Expresion derecha;

    /**
     * Único contructor de la clase
     *
     * @param izquierda Izquierda
     * @param menosIgual MenosIgual
     * @param derecha Derecha
     */
    public MenosIgual(Expresion izquierda, Terminal menosIgual, Expresion derecha) {
        setIzquierda(izquierda);
        setMenosIgual(menosIgual);
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
     * Obtiene el menosIgual
     *
     * @return MenosIgual
     */
    public Terminal getMenosIgual() {
        return menosIgual;
    }

    /**
     * Establece el menosIgual
     *
     * @param menosIgual MenosIgual
     */
    public void setMenosIgual(Terminal menosIgual) {
        menosIgual.setPadre(this);
        this.menosIgual = menosIgual;
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
        return new Simbolo[]{izquierda, menosIgual, derecha};
    }

}
