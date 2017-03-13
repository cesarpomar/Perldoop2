package perldoop.generacion.binario;

import perldoop.generacion.util.Casting;
import perldoop.modelo.arbol.binario.BinAnd;
import perldoop.modelo.arbol.binario.BinDespD;
import perldoop.modelo.arbol.binario.BinDespI;
import perldoop.modelo.arbol.binario.BinNot;
import perldoop.modelo.arbol.binario.BinOr;
import perldoop.modelo.arbol.binario.BinXor;
import perldoop.modelo.arbol.binario.Binario;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de binario
 *
 * @author César Pomar
 */
public class GenBinario {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenBinario(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    /**
     * Genera la operacion binaria
     *
     * @param izq Expresion izquierda
     * @param s Simbolo binario
     * @param der Expresion derecha
     */
    public void genBinario(Expresion izq, Binario s, Expresion der) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(Casting.casting(izq, s.getTipo(), !tabla.getOpciones().isOptNulos()));
        codigo.append(s.getOperador());
        codigo.append(Casting.casting(der, s.getTipo(), !tabla.getOpciones().isOptNulos()));
        s.setCodigoGenerado(codigo);
    }

    public void visitar(BinOr s) {
        genBinario(s.getIzquierda(), s, s.getDerecha());
    }

    public void visitar(BinAnd s) {
        genBinario(s.getIzquierda(), s, s.getDerecha());
    }

    public void visitar(BinNot s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getOperador());
        codigo.append(Casting.casting(s.getExpresion(), s.getTipo(), !tabla.getOpciones().isOptNulos()));
        s.setCodigoGenerado(codigo);
    }

    public void visitar(BinXor s) {
        genBinario(s.getIzquierda(), s, s.getDerecha());
    }

    public void visitar(BinDespI s) {
        genBinario(s.getIzquierda(), s, s.getDerecha());
    }

    public void visitar(BinDespD s) {
        genBinario(s.getIzquierda(), s, s.getDerecha());
    }

}
