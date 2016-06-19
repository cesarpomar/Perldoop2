package perldoop.semantica.flujo;

import perldoop.modelo.arbol.flujo.*;
import perldoop.semantica.TablaSemantica;

/**
 * Clase para la semantica de flujo
 *
 * @author César Pomar
 */
public class SemFlujo {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemFlujo(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(Next s) {
    }

    public void visitar(Last s) {
    }

    public void visitar(Return s) {
    }
}
