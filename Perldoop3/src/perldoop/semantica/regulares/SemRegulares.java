package perldoop.semantica.regulares;

import perldoop.modelo.arbol.regulares.*;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de regulares
 *
 * @author César Pomar
 */
public class SemRegulares {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemRegulares(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(RegularNoMatch s) {
    }

    public void visitar(RegularMatch s) {
    }

    public void visitar(RegularSubs s) {
    }

    public void visitar(RegularTrans s) {
    }
}
