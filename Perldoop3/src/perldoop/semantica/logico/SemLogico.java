package perldoop.semantica.logico;

import perldoop.modelo.arbol.logico.*;
import perldoop.semantica.TablaSemantica;

/**
 * Clase para la semantica de logico
 *
 * @author César Pomar
 */
public class SemLogico {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemLogico(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(LogOr s) {
    }

    public void visitar(LogAnd s) {
    }

    public void visitar(LogNot s) {
    }

    public void visitar(LogOrBajo s) {
    }

    public void visitar(LogAndBajo s) {
    }

    public void visitar(LogNotBajo s) {
    }

    public void visitar(LogXorBajo s) {
    }

    public void visitar(LogTernario s) {
    }
}
