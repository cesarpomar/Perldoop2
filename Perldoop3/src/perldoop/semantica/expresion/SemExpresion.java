package perldoop.semantica.expresion;

import perldoop.modelo.arbol.expresion.*;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;

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

    public void visitar(ExpConstante s) {
        s.setTipo(new Tipo(s.getConstante().getTipo()));
        s.getTipo().setConstante(s.getTipo().isConstante());
    }

    public void visitar(ExpVariable s) {
        s.setTipo(new Tipo(s.getVariable().getTipo()));
        s.getTipo().setVariable(s.getTipo().isVariable());
    }

    public void visitar(ExpAsignacion s) {
//        s.setTipo(new Tipo(s.getAsignacion().getTipo()));
    }

    public void visitar(ExpBinario s) {
//        s.setTipo(new Tipo(s.getBinario().getTipo()));
    }

    public void visitar(ExpAritmetica s) {
        s.setTipo(new Tipo(s.getAritmetica().getTipo()));
    }

    public void visitar(ExpLogico s) {
//        s.setTipo(new Tipo(s.getLogico().getTipo()));
    }

    public void visitar(ExpComparacion s) {
//        s.setTipo(new Tipo(s.getComparacion().getTipo()));
    }

    public void visitar(ExpColeccion s) {
        s.setTipo(new Tipo(s.getColeccion().getTipo()));
    }

    public void visitar(ExpAcceso s) {
//        s.setTipo(new Tipo(s.getAcceso().getTipo()));
    }

    public void visitar(ExpFuncion s) {
//        s.setTipo(new Tipo(s.getFuncion().getTipo()));
    }

    public void visitar(ExpFuncion5 s) {
//        s.setTipo(new Tipo(s.getFuncion().getTipo()));
    }

    public void visitar(ExpRegulares s) {
//        s.setTipo(new Tipo(s.getRegulares().getTipo()));
    }
}
