package perldoop.modelo.arbol.asignacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; asignacion : expresion MOD_IGUAL expresion
 *
 * @author César Pomar
 */
public final class ModIgual extends Asignacion {

    private Expresion izquierda;
    private Terminal modIgual;
    private Expresion derecha;

    /**
     * Único contructor de la clase
     *
     * @param izquierda Izquierda
     * @param modIgual ModIgual
     * @param derecha Derecha
     */
    public ModIgual(Expresion izquierda, Terminal modIgual, Expresion derecha) {
        setIzquierda(izquierda);
        setModIgual(modIgual);
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
     * Obtiene el modIgual
     *
     * @return ModIgual
     */
    public Terminal getModIgual() {
        return modIgual;
    }

    /**
     * Establece el modIgual
     *
     * @param modIgual ModIgual
     */
    public void setModIgual(Terminal modIgual) {
        modIgual.setPadre(this);
        this.modIgual = modIgual;
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
        return new Simbolo[]{izquierda, modIgual, derecha};
    }

}
