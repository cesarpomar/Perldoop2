package perldoop.modelo.arbol.constante;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; asignacion : ENTERO
 *
 * @author César Pomar
 */
public final class Entero extends Constante {

    private Terminal entero;

    /**
     * Único contructor de la clase
     *
     * @param entero Entero
     */
    public Entero(Terminal entero) {
        setEntero(entero);
    }

    /**
     * Obtiene la entero
     *
     * @return Entero
     */
    public Terminal getEntero() {
        return entero;
    }

    /**
     * Establece la entero
     *
     * @param entero Entero
     */
    public void setEntero(Terminal entero) {
        entero.setPadre(this);
        this.entero = entero;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{entero};
    }

}
