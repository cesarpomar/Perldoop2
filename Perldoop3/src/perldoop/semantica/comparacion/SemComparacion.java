package perldoop.semantica.comparacion;

import perldoop.modelo.arbol.comparacion.*;
import perldoop.semantica.TablaSemantica;

/**
 * Clase para la semantica de comparacion
 *
 * @author César Pomar
 */
public class SemComparacion {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemComparacion(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(CompNumEq s) {
    }

    public void visitar(CompNumNe s) {
    }

    public void visitar(CompNumLt s) {
    }

    public void visitar(CompNumLe s) {
    }

    public void visitar(CompNumGt s) {
    }

    public void visitar(CompNumGe s) {
    }

    public void visitar(CompNumCmp s) {
    }

    public void visitar(CompStrEq s) {
    }

    public void visitar(CompStrNe s) {
    }

    public void visitar(CompStrLt s) {
    }

    public void visitar(CompStrLe s) {
    }

    public void visitar(CompStrGt s) {
    }

    public void visitar(CompStrGe s) {
    }

    public void visitar(CompStrCmp s) {
    }

    public void visitar(CompSmart s) {
    }
}
