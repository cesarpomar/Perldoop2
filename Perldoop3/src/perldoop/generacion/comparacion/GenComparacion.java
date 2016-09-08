package perldoop.generacion.comparacion;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.comparacion.CompNumCmp;
import perldoop.modelo.arbol.comparacion.CompNumEq;
import perldoop.modelo.arbol.comparacion.CompNumGe;
import perldoop.modelo.arbol.comparacion.CompNumGt;
import perldoop.modelo.arbol.comparacion.CompNumLe;
import perldoop.modelo.arbol.comparacion.CompNumLt;
import perldoop.modelo.arbol.comparacion.CompNumNe;
import perldoop.modelo.arbol.comparacion.CompSmart;
import perldoop.modelo.arbol.comparacion.CompStrCmp;
import perldoop.modelo.arbol.comparacion.CompStrEq;
import perldoop.modelo.arbol.comparacion.CompStrGe;
import perldoop.modelo.arbol.comparacion.CompStrGt;
import perldoop.modelo.arbol.comparacion.CompStrLe;
import perldoop.modelo.arbol.comparacion.CompStrLt;
import perldoop.modelo.arbol.comparacion.CompStrNe;

/**
 * Clase generadora de comparacion
 * @author César Pomar
 */
public class GenComparacion {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenComparacion(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(CompNumEq s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CompNumNe s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CompNumLt s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CompNumLe s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CompNumGt s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CompNumGe s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CompNumCmp s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CompStrEq s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CompStrNe s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CompStrLt s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CompStrLe s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CompStrGt s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CompStrGe s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CompStrCmp s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CompSmart s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
