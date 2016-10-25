package perldoop.modelo.arbol.std;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;

/**
 * Clase abtracta que representa todas las reduciones de std
 *
 * @author César Pomar
 */
public abstract class Std extends Simbolo {

    protected Terminal handle;

    /**
     * Contructor unico de la clase
     *
     * @param handle Handle
     */
    public Std(Terminal handle) {
        setHandle(handle);
    }

    /**
     * Obtiene el handle
     *
     * @return Handle
     */
    public final Terminal getHandle() {
        return handle;
    }

    /**
     * Establece el handle
     *
     * @param handle Handle
     */
    public final void setHandle(Terminal handle) {
        handle.setPadre(this);
        this.handle = handle;
    }

}
