package perldoop.semantica.varmulti;

import perldoop.modelo.arbol.varmulti.VarMultiMy;
import perldoop.modelo.arbol.varmulti.VarMultiOur;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de multiples variables en declaración
 *
 * @author César Pomar
 */
public class SemVarMulti {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemVarMulti(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(VarMultiMy s) {

    }

    public void visitar(VarMultiOur s) {

    }
}
