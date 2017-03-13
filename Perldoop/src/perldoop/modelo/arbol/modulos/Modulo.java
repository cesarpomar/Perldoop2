package perldoop.modelo.arbol.modulos;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;

/**
 * Clase abtracta que representa todas las reduciones de operaciones de modulos
 *
 * @author César Pomar
 */
public abstract class Modulo extends Simbolo {

    protected Terminal id;

    /**
     * Constructor por defecto
     *
     * @param id Return
     */
    public Modulo(Terminal id) {
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
