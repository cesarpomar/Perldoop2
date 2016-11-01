package perldoop.generacion.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.expresion.*;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.sentencia.StcLista;
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

    /**
     * Prepara las expresiones en caso de que sean sentencias
     *
     * @param s Simbolo para expresion
     * @return Codigo valido para sentencia en caso de ser necesario
     */
    public StringBuilder checkSentencia(Simbolo s) {
        if (s.getPadre() instanceof Lista && s.getPadre().getPadre() instanceof StcLista) {
            return new StringBuilder("Pd.eval(").append(s).append(")");
        }
        return s.getCodigoGenerado();
    }

    public void visitar(ExpCadena s) {
        s.setCodigoGenerado(checkSentencia(s.getCadena()));
    }

    public void visitar(ExpNumero s) {
        s.setCodigoGenerado(checkSentencia(s.getNumero()));
    }

    public void visitar(ExpVariable s) {
        s.setCodigoGenerado(checkSentencia(s.getVariable()));
    }

    public void visitar(ExpAsignacion s) {
        s.setCodigoGenerado(checkSentencia(s.getAsignacion()));
    }

    public void visitar(ExpBinario s) {
        s.setCodigoGenerado(checkSentencia(s.getBinario()));
    }

    public void visitar(ExpAritmetica s) {
        s.setCodigoGenerado(checkSentencia(s.getAritmetica()));
    }

    public void visitar(ExpComparacion s) {
        s.setCodigoGenerado(checkSentencia(s.getComparacion()));
    }

    public void visitar(ExpLogico s) {
        s.setCodigoGenerado(checkSentencia(s.getLogico()));
    }

    public void visitar(ExpColeccion s) {
        s.setCodigoGenerado(checkSentencia(s.getColeccion()));
    }

    public void visitar(ExpAcceso s) {
        s.setCodigoGenerado(checkSentencia(s.getAcceso()));
    }

    public void visitar(ExpFuncion s) {
        s.setCodigoGenerado(checkSentencia(s.getFuncion()));
    }

    public void visitar(ExpFuncion5 s) {
        s.setCodigoGenerado(checkSentencia(s.getFuncion()));
    }

    public void visitar(ExpRegulares s) {
        s.setCodigoGenerado(checkSentencia(s.getRegulares()));
    }

    public void visitar(ExpLectura s) {
        s.setCodigoGenerado(checkSentencia(s.getLectura()));
    }

    public void visitar(ExpStd s) {
        s.setCodigoGenerado(checkSentencia(s.getStd()));
    }

    public void visitar(ExpRango s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
