package perldoop.generacion.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.acceso.AccesoCol;
import perldoop.modelo.arbol.acceso.AccesoColRef;
import perldoop.modelo.arbol.aritmetica.AritNegativo;
import perldoop.modelo.arbol.aritmetica.AritOpUnitario;
import perldoop.modelo.arbol.aritmetica.AritPositivo;
import perldoop.modelo.arbol.coleccion.ColDec;
import perldoop.modelo.arbol.expresion.*;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.arbol.variable.VarMy;
import perldoop.modelo.arbol.variable.VarOur;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.util.Buscar;

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
     * Prepara las expresiones en caso de que sean sentencias y establece su codigo
     *
     * @param exp Expresion
     * @param check Comprobacion extra
     */
    public void genExpresion(Expresion exp, boolean check) {
        Simbolo uso = Buscar.getUso(exp);
        if (check && uso instanceof Lista && uso.getPadre() instanceof StcLista) {
            exp.setCodigoGenerado(new StringBuilder("Pd.eval(").append(exp.getValor()).append(")"));
        } else {
            exp.setCodigoGenerado(exp.getValor().getCodigoGenerado());
        }
    }

    public void visitar(ExpCadena s) {
        genExpresion(s, true);
    }

    public void visitar(ExpNumero s) {
        genExpresion(s, true);
    }

    public void visitar(ExpVariable s) {
        genExpresion(s, !(s.getVariable() instanceof VarMy || s.getVariable() instanceof VarOur));
    }

    public void visitar(ExpAsignacion s) {
        genExpresion(s, false);
    }

    public void visitar(ExpBinario s) {
        genExpresion(s, true);
    }

    public void visitar(ExpAritmetica s) {
        genExpresion(s, !(s.getAritmetica() instanceof AritOpUnitario
                && !(s.getAritmetica() instanceof AritPositivo)
                && !(s.getAritmetica() instanceof AritNegativo))
        );
    }

    public void visitar(ExpComparacion s) {
        genExpresion(s, true);
    }

    public void visitar(ExpLogico s) {
        genExpresion(s, true);
    }

    public void visitar(ExpColeccion s) {
        genExpresion(s, !(s.getColeccion() instanceof ColDec));
    }

    public void visitar(ExpAcceso s) {
        genExpresion(s, (s.getAcceso() instanceof AccesoCol || s.getAcceso() instanceof AccesoColRef)
                && s.getAcceso().getExpresion().getTipo().isArray());
    }

    public void visitar(ExpFuncion s) {
        genExpresion(s, false);
    }

    public void visitar(ExpFuncion5 s) {
        genExpresion(s, false);
    }

    public void visitar(ExpRegulares s) {
        genExpresion(s, false);
    }

    public void visitar(ExpLectura s) {
        genExpresion(s, false);
    }

    public void visitar(ExpStd s) {
        genExpresion(s, true);
    }

    public void visitar(ExpRango s) {
        genExpresion(s, false);
    }

}
