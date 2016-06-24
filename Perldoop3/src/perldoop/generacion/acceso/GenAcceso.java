package perldoop.generacion.acceso;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.acceso.AccesoArray;
import perldoop.modelo.arbol.acceso.AccesoArrayRef;
import perldoop.modelo.arbol.acceso.AccesoMap;
import perldoop.modelo.arbol.acceso.AccesoMapRef;
import perldoop.modelo.arbol.acceso.AccesoRefArray;
import perldoop.modelo.arbol.acceso.AccesoRefEscalar;
import perldoop.modelo.arbol.acceso.AccesoRefMap;
import perldoop.modelo.arbol.acceso.AccesoSigil;

/**
 * Clase generadora de acceso
 * @author César Pomar
 */
public class GenAcceso {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenAcceso(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(AccesoMap s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AccesoArray s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AccesoMapRef s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AccesoArrayRef s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AccesoRefEscalar s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AccesoRefArray s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AccesoSigil s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AccesoRefMap s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
