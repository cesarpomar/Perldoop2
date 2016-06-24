package perldoop.semantica.elsif;

import perldoop.modelo.arbol.elsif.*;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de elsIf
 *
 * @author César Pomar
 */
public class SemElsIf {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemElsIf(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(ElsIfNada s) {
    }

    public void visitar(ElsIfElsIf s) {
    }

    public void visitar(ElsIfElse s) {
    }
}
