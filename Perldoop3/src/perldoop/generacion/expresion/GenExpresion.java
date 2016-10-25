package perldoop.generacion.expresion;

import perldoop.modelo.arbol.expresion.*;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de expresion
 *
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

    public void visitar(ExpCadena s) {
        s.setCodigoGenerado(s.getCadena().getCodigoGenerado());
    }

    public void visitar(ExpNumero s) {
        s.setCodigoGenerado(s.getNumero().getCodigoGenerado());
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

    public void visitar(ExpLectura s) {
        s.setCodigoGenerado(s.getLectura().getCodigoGenerado());
    }

    public void visitar(ExpStd s) {
        s.setCodigoGenerado(s.getStd().getCodigoGenerado());
    }

    public void visitar(ExpVarMulti s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ExpRango s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
