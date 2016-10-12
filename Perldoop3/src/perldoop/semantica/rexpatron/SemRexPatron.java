package perldoop.semantica.rexpatron;

import perldoop.modelo.arbol.rexpatron.RexPatron;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de rexpatron
 *
 * @author César Pomar
 */
public class SemRexPatron {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemRexPatron(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(RexPatron s) {
    }
}
