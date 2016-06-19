package perldoop.semantica.binario;

import perldoop.modelo.arbol.binario.*;
import perldoop.semantica.TablaSemantica;

/**
 * Clase para la semantica de binario
 *
 * @author César Pomar
 */
public class SemBinario {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemBinario(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(BinOr s) {
    }

    public void visitar(BinAnd s) {
    }

    public void visitar(BinNot s) {
    }

    public void visitar(BinXor s) {
    }

    public void visitar(BinDespI s) {
    }

    public void visitar(BinDespD s) {
    }
}
