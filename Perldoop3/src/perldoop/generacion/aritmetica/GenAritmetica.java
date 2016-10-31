package perldoop.generacion.aritmetica;

import perldoop.generacion.acceso.GenAcceso;
import perldoop.generacion.asignacion.GenIgual;
import perldoop.generacion.util.Casting;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.SimboloAux;
import perldoop.modelo.arbol.Terminal;
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
import perldoop.modelo.arbol.aritmetica.Aritmetica;
import perldoop.modelo.arbol.expresion.ExpAcceso;
import perldoop.modelo.arbol.expresion.ExpVariable;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

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

    /**
     * Genera una operacion de preIncremento o preDecremento de una variable
     *
     * @param s Simbolo aritmetico
     * @param expresion Expresion
     * @return Codigo generador
     */
    private StringBuilder genPre(Aritmetica s, Expresion expresion) {
        StringBuilder codigo = new StringBuilder(100);
        boolean opt = tabla.getOpciones().isOptNulos();
        boolean arrayVar = Buscar.isArrayOrVar(expresion);
        Tipo t = s.getTipo();
        if ((t.isInteger() || t.isInteger()) && opt && arrayVar && Buscar.isRepetible(expresion)) {
            codigo.append(s.getOperador()).append(expresion);
        } else {
            SimboloAux lectura;
            SimboloAux escritura;
            Tipo tn=s.getTipo().isNumberType()?s.getTipo():new Tipo(Tipo.DOUBLE);
            if (expresion instanceof ExpVariable) {
                lectura = new SimboloAux(expresion);
                escritura = new SimboloAux(expresion);
            } else {
                lectura = new SimboloAux(t);
                escritura = new SimboloAux(t);
                GenAcceso.getReplica((ExpAcceso) expresion, lectura, escritura, tabla);
            }
            lectura.setCodigoGenerado(Casting.casting(lectura, tn));
            if (!opt) {
                lectura.setCodigoGenerado(Casting.checkNull(lectura, !opt));
            }
            lectura.setTipo(tn);
            lectura.getCodigoGenerado().append(s.getOperador().getValor().charAt(0)).append(s.getOperador().getComentario()).append("1");
            lectura.setCodigoGenerado(Casting.casting(lectura, t));
            if (arrayVar) {
                codigo.append(escritura).append("=").append(lectura);
            } else {
                codigo.append(escritura).append(lectura).append(")");
            }
        }
        return codigo;
    }

    /**
     * Genera una operacion de preIncremento o preDecremento de una variable
     *
     * @param s Simbolo aritmetico
     * @param expresion Expresion
     * @return Codigo generador
     */
    private StringBuilder genPos(Aritmetica s, Expresion expresion) {
        StringBuilder codigo;
        boolean opt = tabla.getOpciones().isOptNulos();
        boolean arrayVar = Buscar.isArrayOrVar(expresion);
        Tipo t = s.getTipo();
        if ((t.isInteger() || t.isInteger()) && opt && arrayVar && Buscar.isRepetible(expresion)) {
            codigo = new StringBuilder(100);
            codigo.append(expresion).append(s.getOperador());
        } else {
            String op = s.getOperador().getValor().equals("++") ? "-" : "+";
            codigo = genPre(s, expresion);
            if (Buscar.getPadre(s, 2) instanceof StcLista) {
                return codigo;
            }
            Tipo tn=s.getTipo().isNumberType()?s.getTipo():new Tipo(Tipo.DOUBLE);
            SimboloAux aux = new SimboloAux(expresion.getTipo(),codigo);
            aux.setCodigoGenerado(Casting.casting(aux, tn).append(op).append("1"));
            aux.setCodigoGenerado(Casting.casting(aux, expresion.getTipo()));
        }
        return codigo;
    }

    public void visitar(AritPreIncremento s) {
        s.setCodigoGenerado(genPre(s, s.getExpresion()));
    }

    public void visitar(AritPreDecremento s) {
        s.setCodigoGenerado(genPre(s, s.getExpresion()));
    }

    public void visitar(AritPostIncremento s) {
        s.setCodigoGenerado(genPos(s, s.getExpresion()));
    }

    public void visitar(AritPostDecremento s) {
        s.setCodigoGenerado(genPos(s, s.getExpresion()));
    }

}
