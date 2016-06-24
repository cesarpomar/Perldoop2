package perldoop.generacion.coleccion;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.coleccion.ColGenerador;
import perldoop.modelo.arbol.coleccion.ColLlave;
import perldoop.modelo.arbol.coleccion.ColMy;
import perldoop.modelo.arbol.coleccion.ColOur;
import perldoop.modelo.arbol.coleccion.ColParentesis;

/**
 * Clase generadora de coleccion
 * @author César Pomar
 */
public class GenColeccion {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenColeccion(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(ColParentesis s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ColCorchete s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ColLlave s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ColGenerador s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ColMy s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ColOur s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
