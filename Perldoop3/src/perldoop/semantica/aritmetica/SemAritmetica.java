package perldoop.semantica.aritmetica;

import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.aritmetica.*;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.util.Tipos;
import perldoop.util.Buscar;

/**
 * Clase para la semantica de aritmetica
 *
 * @author César Pomar
 */
public class SemAritmetica {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemAritmetica(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    /**
     * Calcula el tipo minimo para las dos expresiones
     *
     * @param s Simbolo
     * @return Tipo
     */
    private void tipoMinimo(AritOpBinario s) {
        Tipo ti = Tipos.asNumber(s.getIzquierda());
        Tipo td = Tipos.asNumber(s.getDerecha());
        if (ti.isDouble() || td.isDouble()) {
            s.setTipo(new Tipo(Tipo.DOUBLE));
        } else if (ti.isFloat() || td.isFloat()) {
            s.setTipo(new Tipo(Tipo.FLOAT));
        } else if (ti.isLong() || td.isLong()) {
            s.setTipo(new Tipo(Tipo.LONG));
        } else if (ti.isInteger() || td.isInteger()) {
            s.setTipo(new Tipo(Tipo.INTEGER));
        }
    }

    /**
     * Comprueba el uso de los operadores de incremento/decremento
     *
     * @param s Simbolo
     */
    private void checkIncDec(AritOpUnitario s) {
        Tipo t = s.getExpresion().getTipo();
        if (!Buscar.isVariable(s.getExpresion())) {
            tabla.getGestorErrores().error(Errores.MODIFICAR_CONSTANTE, Buscar.tokenInicio(s), s.getOperador().toString());
            throw new ExcepcionSemantica(Errores.MODIFICAR_CONSTANTE);
        }
        if (!t.isSimple()) {
            tabla.getGestorErrores().error(Errores.MODIFICAR_DEFERENCIACION, Buscar.tokenInicio(s), s.getOperador().toString());
            throw new ExcepcionSemantica(Errores.MODIFICAR_DEFERENCIACION);
        }
        s.setTipo(t);
    }

    public void visitar(AritSuma s) {
        tipoMinimo(s);
    }

    public void visitar(AritResta s) {
        tipoMinimo(s);
    }

    public void visitar(AritMulti s) {
        tipoMinimo(s);
    }

    public void visitar(AritDiv s) {
        tipoMinimo(s);
        Simbolo uso = Buscar.getPadre(s, 1);
        if (uso instanceof Igual) {
            Tipo tigual = ((Igual) uso).getIzquierda().getTipo();
            if (tigual.isInteger() || tigual.isLong()) {
                s.setTipo(tigual);
            }
        }
    }

    public void visitar(AritPow s) {
        s.setTipo(new Tipo(Tipo.DOUBLE));
    }

    public void visitar(AritX s) {
        s.setTipo(new Tipo(Tipo.STRING));
    }

    public void visitar(AritConcat s) {
        s.setTipo(new Tipo(Tipo.STRING));
    }

    public void visitar(AritMod s) {
        Tipo t = Tipos.asNumber(s.getIzquierda());
        Tipo t2 = Tipos.asNumber(s.getDerecha());
        if (t.isInteger() && t2.isInteger()) {
            s.setTipo(t);
        } else {
            s.setTipo(new Tipo(Tipo.LONG));
        }
    }

    public void visitar(AritPositivo s) {
        s.setTipo(Tipos.asNumber(s));
    }

    public void visitar(AritNegativo s) {
        s.setTipo(Tipos.asNumber(s));
    }

    public void visitar(AritPreIncremento s) {
        checkIncDec(s);
    }

    public void visitar(AritPreDecremento s) {
        checkIncDec(s);
    }

    public void visitar(AritPostIncremento s) {
        checkIncDec(s);
    }

    public void visitar(AritPostDecremento s) {
        checkIncDec(s);
    }
}
