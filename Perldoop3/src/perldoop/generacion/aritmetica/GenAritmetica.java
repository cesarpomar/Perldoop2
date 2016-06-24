package perldoop.generacion.aritmetica;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.aritmetica.AritConcat;
import perldoop.modelo.arbol.aritmetica.AritDiv;
import perldoop.modelo.arbol.aritmetica.AritMod;
import perldoop.modelo.arbol.aritmetica.AritMulti;
import perldoop.modelo.arbol.aritmetica.AritNegativo;
import perldoop.modelo.arbol.aritmetica.AritPositivo;
import perldoop.modelo.arbol.aritmetica.AritPostDecremento;
import perldoop.modelo.arbol.aritmetica.AritPostIncremento;
import perldoop.modelo.arbol.aritmetica.AritPow;
import perldoop.modelo.arbol.aritmetica.AritPreDecremento;
import perldoop.modelo.arbol.aritmetica.AritPreIncremento;
import perldoop.modelo.arbol.aritmetica.AritResta;
import perldoop.modelo.arbol.aritmetica.AritSuma;
import perldoop.modelo.arbol.aritmetica.AritX;

/**
 * Clase generadora de aritmetica
 * @author César Pomar
 */
public class GenAritmetica {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenAritmetica(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(AritSuma s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AritResta s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AritDiv s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AritPow s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AritX s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AritConcat s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AritMod s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AritPositivo s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AritNegativo s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AritPreIncremento s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AritPreDecremento s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AritPostIncremento s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AritPostDecremento s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AritMulti s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
