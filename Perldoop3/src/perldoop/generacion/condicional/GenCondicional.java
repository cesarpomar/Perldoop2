package perldoop.generacion.condicional;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.condicional.CondicionalIf;
import perldoop.modelo.arbol.condicional.CondicionalUnless;

/**
 * Clase generadora de condicional
 * @author César Pomar
 */
public class GenCondicional {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenCondicional(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(CondicionalIf s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CondicionalUnless s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
