package perldoop.semantica.aritmetica;

import perldoop.modelo.arbol.aritmetica.*;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de aritmetica
 *
 * @author César Pomar
 */
public class SemAritmetica {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemAritmetica(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(AritSuma s) {
    }

    public void visitar(AritResta s) {
    }

    public void visitar(AritMulti s) {
    }

    public void visitar(AritDiv s) {
    }

    public void visitar(AritPow s) {
    }

    public void visitar(AritX s) {
    }

    public void visitar(AritConcat s) {
    }

    public void visitar(AritMod s) {
    }

    public void visitar(AritPositivo s) {
    }

    public void visitar(AritNegativo s) {
    }

    public void visitar(AritPreIncremento s) {
    }

    public void visitar(AritPreDecremento s) {
    }

    public void visitar(AritPostIncremento s) {
    }

    public void visitar(AritPostDecremento s) {
    }
}
