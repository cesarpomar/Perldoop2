package perldoop.semantica.variable;

import perldoop.modelo.arbol.variable.*;
import perldoop.semantica.TablaSemantica;

/**
 * Clase para la semantica de variable
 *
 * @author César Pomar
 */
public class SemVariable {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemVariable(TablaSemantica tabla) {
        this.tabla = tabla;
    }
    
        public void visitar(VarExistente s){}

    public void visitar(VarPaquete s){}

    public void visitar(VarMy s){}

    public void visitar(VarOur s){}
}
