package perldoop.semantica.expresion;

import perldoop.modelo.arbol.expresion.*;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de expresion
 *
 * @author César Pomar
 */
public class SemExpresion {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemExpresion(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(ExpNumero s) {
        s.setTipo(s.getNumero().getTipo());
    }

    public void visitar(ExpCadena s) {
        s.setTipo(s.getCadena().getTipo());
    }

    public void visitar(ExpVariable s) {
        s.setTipo(s.getVariable().getTipo());
    }

    public void visitar(ExpAsignacion s) {
        s.setTipo(s.getAsignacion().getTipo());
    }

    public void visitar(ExpBinario s) {
        s.setTipo(s.getBinario().getTipo());
    }

    public void visitar(ExpAritmetica s) {
        s.setTipo(s.getAritmetica().getTipo());
    }

    public void visitar(ExpLogico s) {
        s.setTipo(s.getLogico().getTipo());
    }

    public void visitar(ExpComparacion s) {
        s.setTipo(s.getComparacion().getTipo());
    }

    public void visitar(ExpColeccion s) {
        s.setTipo(s.getColeccion().getTipo());
    }

    public void visitar(ExpAcceso s) {
        s.setTipo(s.getAcceso().getTipo());
    }

    public void visitar(ExpFuncion s) {
        s.setTipo(s.getFuncion().getTipo());
    }

    public void visitar(ExpFuncion5 s) {
        s.setTipo(s.getFuncion().getTipo());
    }

    public void visitar(ExpRegulares s) {
        s.setTipo(s.getRegulares().getTipo());
    }

    public void visitar(ExpVarMulti s) {
        s.setTipo(s.getVariables().getTipo());
    }

    public void visitar(ExpRango s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
