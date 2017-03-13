package perldoop.modelo.arbol.lectura;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;

/**
 * Clase abtracta que representa todas las reduciones de lectura
 *
 * @author César Pomar
 */
public abstract class Lectura extends Simbolo {

    protected Terminal menor;
    protected Terminal mayor;

    /**
     * Contructor unico de la clase
     *
     * @param menor Menor 
     * @param mayor Mayor 
     */
    public Lectura(Terminal menor, Terminal mayor) {
        setMenor(menor);
        setMayor(mayor);
    }

    /**
     * Obtiene el menor
     *
     * @return Menor
     */
    public final Terminal getMenor() {
        return menor;
    }

    /**
     * Establece el menor
     *
     * @param menor Menor
     */
    public final void setMenor(Terminal menor) {
        menor.setPadre(this);
        this.menor = menor;
    }

    /**
     * Obtiene el mayor
     *
     * @return Mayor
     */
    public final Terminal getMayor() {
        return mayor;
    }

    /**
     * Establece el mayor
     *
     * @param mayor Mayor
     */
    public final void setMayor(Terminal mayor) {
        mayor.setPadre(this);
        this.mayor = mayor;
    }

}
