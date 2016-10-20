package perldoop.semantica.handle;

import perldoop.modelo.arbol.handle.*;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de handle
 *
 * @author César Pomar
 */
public class SemHandle {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemHandle(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(HandleOut s) {
    }

    public void visitar(HandleErr s) {
    }

    public void visitar(HandleFile s) {
    }

}
