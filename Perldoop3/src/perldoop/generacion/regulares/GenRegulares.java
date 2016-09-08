package perldoop.generacion.regulares;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.regulares.RegularMatch;
import perldoop.modelo.arbol.regulares.RegularNoMatch;
import perldoop.modelo.arbol.regulares.RegularSubs;
import perldoop.modelo.arbol.regulares.RegularTrans;

/**
 * Clase generadora de regulares
 * @author César Pomar
 */
public class GenRegulares {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenRegulares(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(RegularMatch s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(RegularNoMatch s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(RegularSubs s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(RegularTrans s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
