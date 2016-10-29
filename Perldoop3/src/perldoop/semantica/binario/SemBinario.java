package perldoop.semantica.binario;

import perldoop.modelo.arbol.binario.*;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.util.Tipos;

/**
 * Clase para la semantica de binario
 *
 * @author César Pomar
 */
public final class SemBinario {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemBinario(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    /**
     * Calcula el tipo de la operacion binaria
     *
     * @param izq Expresion izquierda
     * @param s Simbolo
     * @param der Expresion derecha
     */
    private void setTipo(Expresion izq, Binario s, Expresion der) {
        Tipo ti = Tipos.asNumber(izq);
        Tipo td = Tipos.asNumber(der);
        if (ti.isInteger() && td.isInteger()) {
            s.setTipo(ti);
        } else {
            s.setTipo(new Tipo(Tipo.LONG));
        }
    }

    public void visitar(BinOr s) {
        setTipo(s.getIzquierda(), s, s.getDerecha());
        Tipos.casting(s.getIzquierda(), s.getTipo(), tabla.getGestorErrores());
        Tipos.casting(s.getDerecha(), s.getTipo(), tabla.getGestorErrores());
    }

    public void visitar(BinAnd s) {
        setTipo(s.getIzquierda(), s, s.getDerecha());
        Tipos.casting(s.getIzquierda(), s.getTipo(), tabla.getGestorErrores());
        Tipos.casting(s.getDerecha(), s.getTipo(), tabla.getGestorErrores());

    }

    public void visitar(BinNot s) {
        Tipo t = Tipos.asNumber(s.getExpresion());
        if (t.isInteger()) {
            s.setTipo(t);
        } else {
            s.setTipo(new Tipo(Tipo.LONG));
        }
    }

    public void visitar(BinXor s) {
        setTipo(s.getIzquierda(), s, s.getDerecha());
        Tipos.casting(s.getIzquierda(), s.getTipo(), tabla.getGestorErrores());
        Tipos.casting(s.getDerecha(), s.getTipo(), tabla.getGestorErrores());
    }

    public void visitar(BinDespI s) {
        Tipo t = Tipos.asNumber(s.getIzquierda());
        if (t.isInteger()) {
            s.setTipo(t);
        } else {
            s.setTipo(new Tipo(Tipo.LONG));
        }
    }

    public void visitar(BinDespD s) {
        Tipo t = Tipos.asNumber(s.getIzquierda());
        if (t.isInteger()) {
            s.setTipo(t);
        } else {
            s.setTipo(new Tipo(Tipo.LONG));
        }
    }
}
