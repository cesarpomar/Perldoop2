package perldoop.modelo.arbol.asignacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -> asignacion : expresion '=' expresion
 *
 * @author César Pomar
 */
public final class Igual extends Asignacion {

    private Expresion izquierda;
    private Terminal igual;
    private Expresion derecha;

    /**
     * Único contructor de la clase
     *
     * @param izquierda Izquierda
     * @param igual Igual
     * @param derecha Derecha
     */
    public Igual(Expresion izquierda, Terminal igual, Expresion derecha) {
        setIzquierda(izquierda);
        setIgual(igual);
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
     * Obtiene el igual
     *
     * @return Igual
     */
    public Terminal getIgual() {
        return igual;
    }

    /**
     * Establece el igual
     *
     * @param igual Igual
     */
    public void setIgual(Terminal igual) {
        igual.setPadre(this);
        this.igual = igual;
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
        return new Simbolo[]{izquierda, igual, derecha};
    }

}
