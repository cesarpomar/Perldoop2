package perldoop.generacion.modificador;

import perldoop.modelo.arbol.modificador.ModFor;
import perldoop.modelo.arbol.modificador.ModIf;
import perldoop.modelo.arbol.modificador.ModNada;
import perldoop.modelo.arbol.modificador.ModUnless;
import perldoop.modelo.arbol.modificador.ModUntil;
import perldoop.modelo.arbol.modificador.ModWhile;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de modificador
 * @author César Pomar
 */
public class GenModificador {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenModificador(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(ModNada s) {
    }

    public void visitar(ModUnless s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ModIf s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ModWhile s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ModUntil s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ModFor s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
