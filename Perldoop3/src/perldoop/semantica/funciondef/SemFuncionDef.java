package perldoop.semantica.funciondef;

import perldoop.modelo.arbol.funciondef.FuncionDef;
import perldoop.semantica.TablaSemantica;

/**
 * Clase para la semantica de funcionDef
 *
 * @author César Pomar
 */
public class SemFuncionDef {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemFuncionDef(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(FuncionDef s) {
    }
}
