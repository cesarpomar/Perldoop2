package perldoop.generacion.binario;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.binario.BinAnd;
import perldoop.modelo.arbol.binario.BinDespD;
import perldoop.modelo.arbol.binario.BinDespI;
import perldoop.modelo.arbol.binario.BinNot;
import perldoop.modelo.arbol.binario.BinOr;
import perldoop.modelo.arbol.binario.BinXor;

/**
 * Clase generadora de binario
 * @author César Pomar
 */
public class GenBinario {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenBinario(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(BinOr s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(BinAnd s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(BinNot s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(BinXor s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(BinDespI s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(BinDespD s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
