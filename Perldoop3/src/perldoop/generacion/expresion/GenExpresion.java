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
import perldoop.modelo.arbol.expresion.ExpRango;
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
     * @param tabla Tabla
     */
    public GenExpresion(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(ExpConstante s) {
        s.setCodigoGenerado(s.getConstante().getCodigoGenerado());
    }

    public void visitar(ExpVariable s) {
        s.setCodigoGenerado(s.getVariable().getCodigoGenerado());
    }

    public void visitar(ExpAsignacion s) {
        s.setCodigoGenerado(s.getAsignacion().getCodigoGenerado());
    }

    public void visitar(ExpBinario s) {
        s.setCodigoGenerado(s.getBinario().getCodigoGenerado());
    }

    public void visitar(ExpAritmetica s) {
        s.setCodigoGenerado(s.getAritmetica().getCodigoGenerado());
    }

    public void visitar(ExpComparacion s) {
        s.setCodigoGenerado(s.getComparacion().getCodigoGenerado());
    }

    public void visitar(ExpLogico s) {
        s.setCodigoGenerado(s.getLogico().getCodigoGenerado());
    }

    public void visitar(ExpColeccion s) {
        s.setCodigoGenerado(s.getColeccion().getCodigoGenerado());
    }

    public void visitar(ExpAcceso s) {
        s.setCodigoGenerado(s.getAcceso().getCodigoGenerado());
    }

    public void visitar(ExpFuncion s) {
        s.setCodigoGenerado(s.getFuncion().getCodigoGenerado());
    }

    public void visitar(ExpFuncion5 s) {
        s.setCodigoGenerado(s.getFuncion().getCodigoGenerado());
    }

    public void visitar(ExpRegulares s) {
        s.setCodigoGenerado(s.getRegulares().getCodigoGenerado());
    }

    public void visitar(ExpRango s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
