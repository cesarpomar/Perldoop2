package perldoop.semantica.condicional;

import perldoop.modelo.arbol.condicional.*;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de condicional
 *
 * @author César Pomar
 */
public class SemCondicional {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemCondicional(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(CondicionalElse s) {
    }

    public void visitar(CondicionalElsif s) {
    }

    public void visitar(CondicionalNada s) {
    }
}
