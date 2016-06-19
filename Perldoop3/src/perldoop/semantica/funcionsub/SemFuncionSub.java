package perldoop.semantica.funcionsub;

import perldoop.modelo.arbol.funcionsub.FuncionSub;
import perldoop.semantica.TablaSemantica;

/**
 * Clase para la semantica de funcionSub
 *
 * @author César Pomar
 */
public class SemFuncionSub {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemFuncionSub(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(FuncionSub s) {
    }
}
