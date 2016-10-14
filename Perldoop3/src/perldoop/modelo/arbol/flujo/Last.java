package perldoop.modelo.arbol.flujo;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; flujo : LAST ';'
 *
 * @author César Pomar
 */
public final class Last extends Flujo {

    private Terminal last;
    private Terminal puntoComa;

    /**
     * Único contructor de la clase
     *
     * @param last Last
     * @param puntoComa PuntoComa
     */
    public Last(Terminal last, Terminal puntoComa) {
        setLast(last);
        setPuntoComa(puntoComa);
    }

    /**
     * Obtiene el last
     *
     * @return Last
     */
    public Terminal getLast() {
        return last;
    }

    /**
     * Estabelce el last
     *
     * @param last Last
     */
    public void setLast(Terminal last) {
        last.setPadre(this);
        this.last = last;
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
        return new Simbolo[]{last};
    }

}
