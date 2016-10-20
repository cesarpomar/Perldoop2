package perldoop.generacion.aritmetica;

import perldoop.generacion.util.Casting;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.aritmetica.AritConcat;
import perldoop.modelo.arbol.aritmetica.AritDiv;
import perldoop.modelo.arbol.aritmetica.AritMod;
import perldoop.modelo.arbol.aritmetica.AritMulti;
import perldoop.modelo.arbol.aritmetica.AritNegativo;
import perldoop.modelo.arbol.aritmetica.AritOpBinario;
import perldoop.modelo.arbol.aritmetica.AritPositivo;
import perldoop.modelo.arbol.aritmetica.AritPostDecremento;
import perldoop.modelo.arbol.aritmetica.AritPostIncremento;
import perldoop.modelo.arbol.aritmetica.AritPow;
import perldoop.modelo.arbol.aritmetica.AritPreDecremento;
import perldoop.modelo.arbol.aritmetica.AritPreIncremento;
import perldoop.modelo.arbol.aritmetica.AritResta;
import perldoop.modelo.arbol.aritmetica.AritSuma;
import perldoop.modelo.arbol.aritmetica.AritX;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Tipo;

/**
 * Clase generadora de aritmetica
 *
 * @author César Pomar
 */
public class GenAritmetica {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenAritmetica(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    /**
     * Genera una expresion numerica
     *
     * @param exp Expresion
     * @param t Tipo
     * @return Numero
     */
    public StringBuilder genExpresionNum(Expresion exp, Tipo t) {
        if (exp.getTipo().isNumberType()) {
            if (tabla.getOpciones().isOptNulos()) {
                return exp.getCodigoGenerado();
            } else {
                return Casting.checkNull(exp);
            }
        } else if (tabla.getOpciones().isOptNulos()) {
            return Casting.casting(exp, t);
        } else {
            return Casting.castingNotNull(exp, t);
        }
    }

    /**
     * Genera las operacion basicas +,-,*
     *
     * @param s Simbolo
     */
    private void genOpBasica(AritOpBinario s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(genExpresionNum(s.getIzquierda(), s.getTipo()));
        codigo.append(s.getOperador());
        codigo.append(genExpresionNum(s.getDerecha(), s.getTipo()));
        s.setCodigoGenerado(codigo);
    }

    public void visitar(AritSuma s) {
        genOpBasica(s);
    }

    public void visitar(AritResta s) {
        genOpBasica(s);
    }

    public void visitar(AritMulti s) {
        genOpBasica(s);
    }

    public void visitar(AritDiv s) {
        genOpBasica(s);
        if (s.getTipo().isDecimal()
                && !s.getTipo().equals(s.getIzquierda().getTipo()) && !s.getTipo().equals(s.getDerecha().getTipo())) {
            StringBuilder codigo = new StringBuilder(100);
            codigo.append("(").append(Tipos.declaracion(s.getTipo())).append(")");
            s.getCodigoGenerado().insert(0, codigo);
        }
    }

    public void visitar(AritPow s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Pd.pow(").append(s.getOperador().getComentario());
        codigo.append(genExpresionNum(s.getIzquierda(), s.getTipo()));
        codigo.append(",");
        codigo.append(genExpresionNum(s.getDerecha(), s.getTipo()));
        codigo.append(")");
        s.setCodigoGenerado(codigo);
    }

    public void visitar(AritX s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Pd.x(").append(s.getOperador().getComentario());
        if (tabla.getOpciones().isOptNulos()) {
            codigo.append(Casting.toString(s.getIzquierda()));
            codigo.append(",");
            codigo.append(Casting.toNumber(s.getDerecha()));
        } else {
            codigo.append(Casting.castingNotNull(s.getIzquierda(), new Tipo(Tipo.STRING)));
            codigo.append(",");
            codigo.append(Casting.castingNotNull(s.getDerecha(), new Tipo(Tipo.NUMBER)));
        }
        codigo.append(")");
        s.setCodigoGenerado(codigo);
    }

    public void visitar(AritConcat s) {
        StringBuilder codigo = new StringBuilder(100);
        if (tabla.getOpciones().isOptNulos()) {
            codigo.append(Casting.toString(s.getIzquierda()));
            codigo.append("+").append(s.getOperador().getComentario());
            codigo.append(Casting.toString(s.getDerecha()));
        } else {
            Tipo t = new Tipo(Tipo.STRING);
            codigo.append(Casting.castingNotNull(s.getIzquierda(), t));
            codigo.append("+").append(s.getOperador().getComentario());
            codigo.append(Casting.castingNotNull(s.getDerecha(), t));
        }
        s.setCodigoGenerado(codigo);
    }

    public void visitar(AritMod s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Pd.mod(").append(s.getOperador().getComentario());
        codigo.append(genExpresionNum(s.getIzquierda(), s.getTipo()));
        codigo.append(',').append(s.getOperador().getComentario());
        codigo.append(genExpresionNum(s.getDerecha(), s.getTipo()));
        codigo.append(")");
        s.setCodigoGenerado(codigo);
    }

    public void visitar(AritPositivo s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getOperador());
        codigo.append(genExpresionNum(s.getExpresion(), s.getTipo()));
        s.setCodigoGenerado(codigo);
    }

    public void visitar(AritNegativo s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getOperador());
        codigo.append(genExpresionNum(s.getExpresion(), s.getTipo()));
        s.setCodigoGenerado(codigo);
    }

    public void visitar(AritPreIncremento s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AritPreDecremento s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AritPostIncremento s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AritPostDecremento s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
