package perldoop.generacion.asignacion;

import perldoop.generacion.acceso.GenAcceso;
import perldoop.generacion.util.Casting;
import perldoop.modelo.arbol.SimboloAux;
import perldoop.modelo.arbol.asignacion.AndIgual;
import perldoop.modelo.arbol.asignacion.Asignacion;
import perldoop.modelo.arbol.asignacion.ConcatIgual;
import perldoop.modelo.arbol.asignacion.DLOrIgual;
import perldoop.modelo.arbol.asignacion.DespDIgual;
import perldoop.modelo.arbol.asignacion.DespIIgual;
import perldoop.modelo.arbol.asignacion.DivIgual;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.asignacion.LAndIgual;
import perldoop.modelo.arbol.asignacion.LOrIgual;
import perldoop.modelo.arbol.asignacion.MasIgual;
import perldoop.modelo.arbol.asignacion.MenosIgual;
import perldoop.modelo.arbol.asignacion.ModIgual;
import perldoop.modelo.arbol.asignacion.MultiIgual;
import perldoop.modelo.arbol.asignacion.OrIgual;
import perldoop.modelo.arbol.asignacion.PowIgual;
import perldoop.modelo.arbol.asignacion.XIgual;
import perldoop.modelo.arbol.asignacion.XorIgual;
import perldoop.modelo.arbol.expresion.ExpAcceso;
import perldoop.modelo.arbol.expresion.ExpVariable;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.util.Tipos;
import perldoop.util.Buscar;

/**
 * Clase generadora de asignacion
 *
 * @author César Pomar
 */
public class GenAsignacion {

    private TablaGenerador tabla;
    private GenIgual genIgual;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenAsignacion(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    /**
     * Genera una signacion con operador
     *
     * @param s Simbolo asignacion
     * @param check Comprobacion de traduccion directa
     * @param tvar Tipo en el que se opera la variable
     * @param tval Tipo en el que se opera el valor
     * @param top Tipo que retorna el operador
     * @param op Nombre del operador
     * @param funcion Es una llamada a funcion
     */
    private void genAsignacion(Asignacion s, boolean check, Tipo tvar, Tipo tval, Tipo top, String op, boolean funcion) {
        StringBuilder codigo = new StringBuilder(100);
        Expresion var = s.getIzquierda();
        Expresion val = s.getDerecha();
        boolean opt = tabla.getOpciones().isOptNulos();
        boolean arrayVar = Buscar.isArrayOrVar(var);
        //Traduccion directa
        if (check && opt && arrayVar) {
            codigo.append(var).append(op).append('=').append(s.getOperador().getComentario()).append(Casting.casting(val, var.getTipo()));
        } else {
            SimboloAux lectura;
            SimboloAux escritura;
            //Separamos la variable en lectura y escritura
            if (var instanceof ExpVariable) {
                lectura = new SimboloAux(var);
                escritura = new SimboloAux(var);
            } else {
                lectura = new SimboloAux(s.getTipo());
                escritura = new SimboloAux(s.getTipo());
                GenAcceso.getReplica((ExpAcceso) var, lectura, escritura, tabla);
            }
            //Comprobamos que la variable no se nula
            lectura.setCodigoGenerado(Casting.casting(lectura, tvar));
            if (!opt) {
                lectura.setCodigoGenerado(Casting.checkNull(lectura, !opt));
            }
            //Construimos como una funcion o como un operador
            if (funcion) {
                StringBuilder aux = new StringBuilder(100).append("Pd.").append(op).append(s.getOperador().getComentario()).append("(");
                lectura.getCodigoGenerado().insert(0, aux).append(",");
            } else {
                lectura.getCodigoGenerado().append(op).append(s.getOperador().getComentario());
            }
            //Comprobamos que no sea nulo el valor
            if (opt) {
                lectura.getCodigoGenerado().append(Casting.casting(val, tval));
            } else {
                lectura.getCodigoGenerado().append(Casting.castingNotNull(val, tval));
            }
            //Si es funcion cerramos parentesis
            if (funcion) {
                lectura.getCodigoGenerado().append(')');
            }
            lectura.setTipo(top);
            lectura.setCodigoGenerado(Casting.casting(lectura, s.getTipo()));
            //Realizamos la asignacion
            if (arrayVar) {
                codigo.append(escritura).append("=").append(lectura);
            } else {
                codigo.append(escritura).append(lectura).append(")");
            }
        }
        s.setCodigoGenerado(codigo);
    }

    /**
     * Obtiene el tipo de una expresion aritmetiva
     *
     * @param var Variable
     * @param val Valor
     * @return Tipo de la operacion
     */
    private Tipo getTipoArit(Expresion var, Expresion val) {
        Tipo tvar = Tipos.asNumber(var);
        Tipo tval = Tipos.asNumber(val);
        if (tvar.isDouble() || tval.isDouble()) {
            return new Tipo(Tipo.DOUBLE);
        } else if (tvar.isFloat() || tval.isFloat()) {
            return new Tipo(Tipo.FLOAT);
        } else if (tvar.isLong() || tval.isLong()) {
            return new Tipo(Tipo.LONG);
        } else if (tvar.isInteger() || tval.isInteger()) {
            return new Tipo(Tipo.INTEGER);
        }
        return null;
    }

    public void visitar(Igual s) {
        if (genIgual == null) {
            genIgual = new GenIgual(tabla);
        }
        genIgual.visitar(s);
    }

    public void visitar(MasIgual s) {
        Expresion var = s.getIzquierda();
        Expresion val = s.getDerecha();
        Tipo tvar = var.getTipo().isNumberType() ? var.getTipo() : new Tipo(Tipo.DOUBLE);
        Tipo tval = val.getTipo().isNumberType() ? val.getTipo() : new Tipo(Tipo.DOUBLE);
        Tipo top = getTipoArit(var, val);
        genAsignacion(s, s.getTipo().isNumberType(), tvar, tval, top, "+", false);
    }

    public void visitar(MenosIgual s) {
        Expresion var = s.getIzquierda();
        Expresion val = s.getDerecha();
        Tipo tvar = var.getTipo().isNumberType() ? var.getTipo() : new Tipo(Tipo.DOUBLE);
        Tipo tval = val.getTipo().isNumberType() ? val.getTipo() : new Tipo(Tipo.DOUBLE);
        Tipo top = getTipoArit(var, val);
        genAsignacion(s, s.getTipo().isNumberType(), tvar, tval, top, "-", false);
    }

    public void visitar(MultiIgual s) {
        Expresion var = s.getIzquierda();
        Expresion val = s.getDerecha();
        Tipo tvar = var.getTipo().isNumberType() ? var.getTipo() : new Tipo(Tipo.DOUBLE);
        Tipo tval = val.getTipo().isNumberType() ? val.getTipo() : new Tipo(Tipo.DOUBLE);
        Tipo top = getTipoArit(var, val);
        genAsignacion(s, s.getTipo().isNumberType(), tvar, tval, top, "*", false);
    }

    public void visitar(DivIgual s) {
        Expresion var = s.getIzquierda();
        Expresion val = s.getDerecha();
        Tipo tvar = var.getTipo().isNumberType() ? var.getTipo() : new Tipo(Tipo.DOUBLE);
        Tipo tval = val.getTipo().isNumberType() ? val.getTipo() : new Tipo(Tipo.DOUBLE);
        Tipo top = getTipoArit(var, val);
        genAsignacion(s, s.getTipo().isNumberType(), tvar, tval, top, "/", false);
    }

    public void visitar(ModIgual s) {
        Tipo tvar = new Tipo(Tipo.INTEGER);
        Tipo tval = new Tipo(Tipo.INTEGER);
        Tipo top = new Tipo(Tipo.INTEGER);
        genAsignacion(s, false, tvar, tval, top, "mod", true);
    }

    public void visitar(PowIgual s) {
        Expresion var = s.getIzquierda();
        Expresion val = s.getDerecha();
        Tipo tvar = var.getTipo().isNumberType() ? var.getTipo() : new Tipo(Tipo.DOUBLE);
        Tipo tval = val.getTipo().isNumberType() ? val.getTipo() : new Tipo(Tipo.DOUBLE);
        Tipo top = new Tipo(Tipo.DOUBLE);
        genAsignacion(s, false, tvar, tval, top, "pow", true);
    }

    public void visitar(AndIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor() + " Not supported yet.");
    }

    public void visitar(OrIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor() + " Not supported yet.");
    }

    public void visitar(XorIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor() + " Not supported yet.");
    }

    public void visitar(DespDIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor() + " Not supported yet.");
    }

    public void visitar(DespIIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor() + " Not supported yet.");
    }

    public void visitar(LOrIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor() + " Not supported yet.");
    }

    public void visitar(DLOrIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor() + " Not supported yet.");
    }

    public void visitar(LAndIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor() + " Not supported yet.");
    }

    public void visitar(XIgual s) {
        Tipo tvar = new Tipo(Tipo.STRING);
        Tipo tval =  new Tipo(Tipo.INTEGER);
        Tipo top = new Tipo(Tipo.STRING);
        genAsignacion(s, false, tvar, tval, top, "x", true);
    }

    public void visitar(ConcatIgual s) {
        Tipo tvar = new Tipo(Tipo.STRING);
        Tipo tval =  new Tipo(Tipo.STRING);
        Tipo top = new Tipo(Tipo.STRING);
        genAsignacion(s, s.getTipo().isString(), tvar, tval, top, "+", false);
    }

}
