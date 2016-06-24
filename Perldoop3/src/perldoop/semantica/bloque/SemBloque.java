package perldoop.semantica.bloque;

import perldoop.modelo.arbol.bloque.*;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de bloque
 *
 * @author César Pomar
 */
public class SemBloque {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemBloque(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(BloqueCondicional s) {
    }

    public void visitar(BloqueWhile s) {
    }

    public void visitar(BloqueUntil s) {
    }

    public void visitar(BloqueDoWhile s) {
    }

    public void visitar(BloqueDoUntil s) {
    }

    public void visitar(BloqueFor s) {
    }

    public void visitar(BloqueForeachVar s) {
    }

    public void visitar(BloqueForeach s) {
    }
}
