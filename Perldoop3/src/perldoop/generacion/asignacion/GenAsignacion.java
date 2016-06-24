package perldoop.generacion.asignacion;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.asignacion.AndIgual;
import perldoop.modelo.arbol.asignacion.ConcatIgual;
import perldoop.modelo.arbol.asignacion.DespDIgual;
import perldoop.modelo.arbol.asignacion.DespIIgual;
import perldoop.modelo.arbol.asignacion.DivIgual;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.asignacion.LAndIgual;
import perldoop.modelo.arbol.asignacion.LOrIgual;
import perldoop.modelo.arbol.asignacion.MasIgual;
import perldoop.modelo.arbol.asignacion.MenosIgual;
import perldoop.modelo.arbol.asignacion.ModIgual;
import perldoop.modelo.arbol.asignacion.MultiIgual;
import perldoop.modelo.arbol.asignacion.OrIgual;
import perldoop.modelo.arbol.asignacion.PowIgual;
import perldoop.modelo.arbol.asignacion.XIgual;
import perldoop.modelo.arbol.asignacion.XorIgual;

/**
 * Clase generadora de asignacion
 * @author César Pomar
 */
public class GenAsignacion {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenAsignacion(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Igual s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(MasIgual s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(MenosIgual s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(MultiIgual s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(DivIgual s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ModIgual s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(PowIgual s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AndIgual s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(OrIgual s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(XorIgual s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(DespDIgual s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(DespIIgual s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(LOrIgual s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(LAndIgual s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(XIgual s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ConcatIgual s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
