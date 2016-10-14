package perldoop.modelo.arbol.flujo;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * /**
 * Clase que representa la reduccion -&gt; flujo : NEXT ';'
 *
 * @author César Pomar
 */
public final class Next extends Flujo {

    private Terminal next;
    private Terminal puntoComa;

    /**
     * Único contructor de la clase
     *
     * @param next Next
     * @param puntoComa PuntoComa
     */
    public Next(Terminal next, Terminal puntoComa) {
        setNext(next);
        setPuntoComa(puntoComa);
    }

    /**
     * Obtiene el next
     *
     * @return Next
     */
    public Terminal getNext() {
        return next;
    }

    /**
     * Establece el next
     *
     * @param next Next
     */
    public void setNext(Terminal next) {
        next.setPadre(this);
        this.next = next;
    }

    /**
     * Obtiene el punto y coma ';'
     *
     * @return PuntoComa
     */
    public Terminal getPuntoComa() {
        return puntoComa;
    }

    /**
     * Establece el punto y coma ';'
     *
     * @param puntoComa PuntoComa
     */
    public void setPuntoComa(Terminal puntoComa) {
        puntoComa.setPadre(this);
        this.puntoComa = puntoComa;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{next};
    }

}
