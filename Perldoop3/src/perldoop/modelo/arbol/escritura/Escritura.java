package perldoop.modelo.arbol.escritura;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;

/**
 * Clase abtracta que representa todas las reduciones de escritura
 *
 * @author César Pomar
 */
public abstract class Escritura extends Simbolo {

    protected Terminal handle;

    /**
     * Contructor unico de la clase
     *
     * @param handle Handle
     */
    public Escritura(Terminal handle) {
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
