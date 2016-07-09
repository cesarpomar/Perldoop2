package perldoop.semantica.funcion;

import perldoop.modelo.arbol.funcion.*;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de funcion
 *
 * @author César Pomar
 */
public class SemFuncion {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemFuncion(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(FuncionPaqueteArgs s) {
    }

    public void visitar(FuncionPaqueteNoArgs s) {
    }

    public void visitar(FuncionArgs s) {
    }

    public void visitar(FuncionNoArgs s) {
    }

    public void visitar(Argumentos s) {
    }
}
