package perldoop.generacion.logico;

import perldoop.generacion.util.Casting;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.SimboloAux;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.logico.DLogOr;
import perldoop.modelo.arbol.logico.LogAnd;
import perldoop.modelo.arbol.logico.LogAndBajo;
import perldoop.modelo.arbol.logico.LogNot;
import perldoop.modelo.arbol.logico.LogNotBajo;
import perldoop.modelo.arbol.logico.LogOr;
import perldoop.modelo.arbol.logico.LogOrBajo;
import perldoop.modelo.arbol.logico.LogTernario;
import perldoop.modelo.arbol.logico.LogXorBajo;
import perldoop.modelo.arbol.logico.Logico;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Clase generadora de logico
 *
 * @author César Pomar
 */
public class GenLogico {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenLogico(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    /**
     * Genera un operador conservando la precedencia
     *
     * @param exp Expresion
     * @param t Tipo
     * @param low Baja precedencia
     * @return Codigo del operando
     */
    private StringBuilder operando(Expresion exp, Tipo t, boolean low) {
        StringBuilder codigo = Casting.casting(exp, t,!tabla.getOpciones().isOptNulos());
        if (low && !Buscar.isVariable(exp) && !(exp instanceof ExpColeccion)) {
            codigo.insert(0, '(').append(')');
        }
        return codigo;
    }

    /**
     * Genera la operacion logica And
     *
     * @param izq Operando izquierdo
     * @param s Simbolo logico
     * @param der Operando derecha
     * @param low Precedencia
     */
    private void genAnd(Expresion izq, Logico s, Expresion der, boolean low) {
        StringBuilder codigo = new StringBuilder(100);
        if (s.getTipo().isBoolean()) {
            codigo.append(operando(izq, s.getTipo(), low));
            if (low) {
                codigo.append("&&").append(s.getOperador().getComentario());
            } else {
                codigo.append(s.getOperador());
            }
            codigo.append(operando(der, s.getTipo(), low));
        } else {
            String aux = tabla.getGestorReservas().getAux();
            tabla.getDeclaraciones().add(Tipos.declaracion(izq.getTipo()).append(" ").append(aux).append(";"));
            SimboloAux check = new SimboloAux(izq);
            check.getCodigoGenerado().insert(0, aux + "=");
            codigo.append("(").append(Casting.casting(check,new Tipo(Tipo.BOOLEAN),!tabla.getOpciones().isOptNulos())).append("?");
            codigo.append(Casting.casting(der, s.getTipo(),!tabla.getOpciones().isOptNulos())).append(")");
            codigo.append(":");
            codigo.append(Casting.casting(new SimboloAux(izq.getTipo(), new StringBuilder(aux)), s.getTipo(),!tabla.getOpciones().isOptNulos()));
        }
        s.setCodigoGenerado(codigo);
    }

    /**
     * Genera la operacion logica Or
     *
     * @param izq Operando izquierdo
     * @param s Simbolo logico
     * @param der Operando derecha
     * @param low Precedencia
     */
    private void genOr(Expresion izq, Logico s, Expresion der, boolean low) {
        StringBuilder codigo = new StringBuilder(100);
        if (s.getTipo().isBoolean()) {
            codigo.append(operando(izq, s.getTipo(), low));
            if (low) {
                codigo.append("||").append(s.getOperador().getComentario());
            } else {
                codigo.append(s.getOperador());
            }
            codigo.append(operando(der, s.getTipo(), low));
        } else {
            String aux = tabla.getGestorReservas().getAux();
            tabla.getDeclaraciones().add(Tipos.declaracion(izq.getTipo()).append(" ").append(aux).append(";"));
            SimboloAux check = new SimboloAux(izq);
            check.getCodigoGenerado().insert(0, aux + "=");
            codigo.append("(").append(Casting.casting(check,new Tipo(Tipo.BOOLEAN),!tabla.getOpciones().isOptNulos())).append("?");
            codigo.append(Casting.casting(new SimboloAux(izq.getTipo(), new StringBuilder(aux)), s.getTipo(),!tabla.getOpciones().isOptNulos()));
            codigo.append(":");
            codigo.append(Casting.casting(der, s.getTipo(),!tabla.getOpciones().isOptNulos())).append(")");
        }
        s.setCodigoGenerado(codigo);
    }

    public void visitar(LogOr s) {
        genOr(s.getIzquierda(), s, s.getDerecha(), false);
    }

    public void visitar(LogAnd s) {
        genAnd(s.getIzquierda(), s, s.getDerecha(), false);
    }

    public void visitar(LogNot s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getOperador());
        codigo.append(Casting.casting(s.getExpresion(), new Tipo(Tipo.BOOLEAN),!tabla.getOpciones().isOptNulos()));
        s.setCodigoGenerado(codigo);
    }

    public void visitar(LogOrBajo s) {
        genOr(s.getIzquierda(), s, s.getDerecha(), true);
    }

    public void visitar(LogAndBajo s) {
        genAnd(s.getIzquierda(), s, s.getDerecha(), true);
    }

    public void visitar(LogNotBajo s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append('!').append(s.getOperador().getComentario());
        codigo.append(Casting.casting(s.getExpresion(),new Tipo(Tipo.BOOLEAN),!tabla.getOpciones().isOptNulos()));
        s.setCodigoGenerado(codigo);
    }

    public void visitar(LogXorBajo s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Pd.xor(");
        codigo.append(Casting.casting(s.getIzquierda(),new Tipo(Tipo.BOOLEAN),!tabla.getOpciones().isOptNulos()));
        codigo.append(",").append(s.getOperador().getComentario());
        codigo.append(Casting.casting(s.getDerecha(),new Tipo(Tipo.BOOLEAN),!tabla.getOpciones().isOptNulos()));
        codigo.append(")");
        s.setCodigoGenerado(codigo);
    }

    public void visitar(LogTernario s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("(").append(Casting.casting(s.getCondicion(),new Tipo(Tipo.BOOLEAN),!tabla.getOpciones().isOptNulos())).append(s.getOperador());
        codigo.append(Casting.casting(s.getCierta(), s.getTipo(),!tabla.getOpciones().isOptNulos()));
        codigo.append(s.getDosPuntos());
        codigo.append(Casting.casting(s.getFalsa(), s.getTipo(),!tabla.getOpciones().isOptNulos()));
        codigo.append(")");        
        s.setCodigoGenerado(codigo);
    }

    public void visitar(DLogOr s) {
        StringBuilder codigo = new StringBuilder(100);
        String aux = tabla.getGestorReservas().getAux();
        tabla.getDeclaraciones().add(Tipos.declaracion(s.getIzquierda().getTipo()).append(" ").append(aux).append(";"));
        SimboloAux check = new SimboloAux(s.getIzquierda());
        check.getCodigoGenerado().insert(0, aux + "=");
        codigo.append("(defined(").append(aux).append("=").append(s.getIzquierda()).append(")?");
        codigo.append(Casting.casting(new SimboloAux(s.getIzquierda().getTipo(), new StringBuilder(aux)), s.getTipo(),!tabla.getOpciones().isOptNulos()));
        codigo.append(":");
        codigo.append(Casting.casting(s.getDerecha(), s.getTipo(),!tabla.getOpciones().isOptNulos())).append(")");
        s.setCodigoGenerado(codigo);
    }

}
