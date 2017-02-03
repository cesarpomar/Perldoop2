package perldoop.modelo.arbol.flujo;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;

/**
 * Clase abtracta que representa todas las reduciones de flujo
 *
 * @author César Pomar
 */
public abstract class Flujo extends Simbolo {

    protected Terminal id;

    /**
     * Constructor por defecto
     *
     * @param id Return
     */
    public Flujo(Terminal id) {
        setId(id);
    }

    /**
     * Obtiene el return
     *
     * @return Return
     */
    public final Terminal getId() {
        return id;
    }

    /**
     * Establece el return
     *
     * @param id Return
     */
    public final void setId(Terminal id) {
        id.setPadre(this);
        this.id = id;
    }
}
