package perldoop.semantica.modificador;

import perldoop.modelo.arbol.modificador.*;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de modificador
 *
 * @author César Pomar
 */
public class SemModificador {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemModificador(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(ModNada s) {
    }

    public void visitar(ModIf s) {
    }

    public void visitar(ModUnless s) {
    }

    public void visitar(ModWhile s) {
    }

    public void visitar(ModUntil s) {
    }

    public void visitar(ModFor s) {
    }
}
