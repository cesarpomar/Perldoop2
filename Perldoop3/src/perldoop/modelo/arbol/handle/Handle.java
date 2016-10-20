package perldoop.modelo.arbol.handle;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;

/**
 * Clase abtracta que representa todas las reduciones de Handle
 *
 * @author César Pomar
 */
public abstract class Handle extends Simbolo {

    protected Terminal std;

    /**
     * Constructor unico de la clase
     *
     * @param std Salida
     */
    public Handle(Terminal std) {
        setStd(std);
    }

    /**
     * Obtiene la salida
     *
     * @return salida
     */
    public final Terminal getStd() {
        return std;
    }

    /**
     * Establece la salida
     *
     * @param std Salida
     */
    public final void setStd(Terminal std) {
        std.setPadre(this);
        this.std = std;
    }
}
