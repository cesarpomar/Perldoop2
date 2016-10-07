package perldoop.generacion.condicional;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.condicional.*;

/**
 * Clase generadora de condicional
 *
 * @author César Pomar
 */
public class GenCondicional {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenCondicional(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(CondicionalElse s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CondicionalElsif s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CondicionalNada s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
