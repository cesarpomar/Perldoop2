package perldoop.generacion.logico;

import perldoop.modelo.arbol.logico.DLogOr;
import perldoop.modelo.arbol.logico.LogAnd;
import perldoop.modelo.arbol.logico.LogAndBajo;
import perldoop.modelo.arbol.logico.LogNot;
import perldoop.modelo.arbol.logico.LogNotBajo;
import perldoop.modelo.arbol.logico.LogOr;
import perldoop.modelo.arbol.logico.LogOrBajo;
import perldoop.modelo.arbol.logico.LogTernario;
import perldoop.modelo.arbol.logico.LogXorBajo;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de logico
 * @author César Pomar
 */
public class GenLogico {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenLogico(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(LogOr s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(LogAnd s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(LogNot s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(LogOrBajo s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(LogAndBajo s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(LogNotBajo s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(LogXorBajo s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(LogTernario s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(DLogOr s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
