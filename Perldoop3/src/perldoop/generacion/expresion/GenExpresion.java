package perldoop.generacion.expresion;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.expresion.ExpAcceso;
import perldoop.modelo.arbol.expresion.ExpAritmetica;
import perldoop.modelo.arbol.expresion.ExpAsignacion;
import perldoop.modelo.arbol.expresion.ExpBinario;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.ExpComparacion;
import perldoop.modelo.arbol.expresion.ExpConstante;
import perldoop.modelo.arbol.expresion.ExpFuncion;
import perldoop.modelo.arbol.expresion.ExpFuncion5;
import perldoop.modelo.arbol.expresion.ExpLogico;
import perldoop.modelo.arbol.expresion.ExpRegulares;
import perldoop.modelo.arbol.expresion.ExpVariable;

/**
 * Clase generadora de expresion
 * @author César Pomar
 */
public class GenExpresion {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenExpresion(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(ExpConstante s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ExpVariable s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ExpAsignacion s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ExpBinario s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ExpAritmetica s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ExpComparacion s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ExpLogico s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ExpColeccion s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ExpAcceso s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ExpFuncion s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ExpFuncion5 s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ExpRegulares s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
