package perldoop.semantica.expresion;

import perldoop.modelo.arbol.expresion.*;
import perldoop.semantica.TablaSemantica;

/**
 * Clase para la semantica de expresion
 *
 * @author César Pomar
 */
public class SemExpresion {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemExpresion(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(ExpConstante s) {
    }

    public void visitar(ExpVariable s) {
    }

    public void visitar(ExpAsignacion s) {
    }

    public void visitar(ExpBinario s) {
    }

    public void visitar(ExpAritmetica s) {
    }

    public void visitar(ExpLogico s) {
    }

    public void visitar(ExpComparacion s) {
    }

    public void visitar(ExpColeccion s) {
    }

    public void visitar(ExpAcceso s) {
    }

    public void visitar(ExpFuncion s) {
    }

    public void visitar(ExpFuncion5 s) {
    }

    public void visitar(ExpRegulares s) {
    }
}
