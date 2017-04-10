package perldoop.generacion.util;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.SimboloAux;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.ExpFuncion;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.lectura.Lectura;
import perldoop.modelo.arbol.regulares.RegularMatch;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Clase para generacion de casting
 *
 * @author César Pomar
 */
public final class Casting {

    /**
     * Transforma una coleccion a escalar retornando su primer o ultimo elemento
     *
     * @param col Simbolo coleccion
     * @param escalar Simbolo escalar
     * @return Primer o ultimo elemento de la colecciones
     */
    public static Simbolo colToScalar(Simbolo col, Simbolo escalar) {
        if (escalar == null || !escalar.getTipo().isColeccion()) {
            if (col.getTipo().isArrayOrList() && col instanceof Expresion) {
                Expresion exp = Buscar.getExpresion((Expresion) col);
                if (exp.getValor() instanceof Lectura) {
                    return new SimboloAux(new Tipo(Tipo.STRING),
                            new StringBuilder(col.getCodigoGenerado()).replace(0, "Pd.readLines".length(), "Pd.read"));
                } else if (!Buscar.isVariable(exp)) {
                    StringBuilder codigo = new StringBuilder(100);
                    char contexto = '@';
                    if (escalar != null && escalar instanceof Expresion) {
                        contexto = Buscar.getContexto(Buscar.getExpresion((Expresion) escalar).getValor());
                    }
                    if (contexto == '$') {
                        Expresion expTest = Buscar.getExpresion((Expresion) col);
                        if (expTest instanceof ExpFuncion || expTest instanceof ExpFuncion) {
                            return new SimboloAux(new Tipo(Tipo.INTEGER), Casting.toInteger(new SimboloAux(col)));
                        } else {
                            codigo.append("Pd.last(").append(col).append(")");
                        }
                    } else {
                        codigo.append("Pd.first(").append(col).append(")");
                    }
                    Tipo t = col.getTipo().getSubtipo(1);
                    if (t.isColeccion()) {
                        t.add(0, Tipo.REF);
                        codigo.insert(0, Tipos.declaracion(t).append("(")).append(")");
                    }
                    return new SimboloAux(t, codigo);
                } else if (exp.getValor() instanceof Igual && ((Igual) exp.getValor()).getIzquierda() instanceof ExpColeccion) {
                    //Cambiar valores multiasignacion por su numero
                    return new SimboloAux(new Tipo(Tipo.INTEGER), new StringBuilder(col.getCodigoGenerado()).replace(0, 3, "Pd.s"));
                }
            }
        }
        return col;
    }

    /**
     * Transforma un escalar retornando su ultimo elemento
     *
     * @param col Simbolo coleccion
     * @return Ultimo elemento de la colecciones
     */
    private static Simbolo colToScalar(Simbolo col) {
        return colToScalar(col, null);
    }

    /**
     * Castea una expresión a boolean
     *
     * @param s Simbolo
     * @return Casting
     */
    public static StringBuilder toBoolean(Simbolo s) {
        if (Buscar.isUndef(s)) {
            return s.getCodigoGenerado();
        }
        if (s.getTipo().isArray() && Buscar.getExpresion((Expresion) s).getValor() instanceof RegularMatch) {
            //Optimizacion para expresiones regulares, en caso de boolean usamos un match rapido
            return new StringBuilder(s.getCodigoGenerado()).replace(0, "Regex.matcher".length(), "Regex.match");
        }
        s = colToScalar(s);
        StringBuilder cst = new StringBuilder(100);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.ARRAY:
                if (Buscar.isNotNull(s)) {
                    return cst.append("(").append(s.getCodigoGenerado()).append(".length > 0)");
                } else {
                    return cst.append("Casting.toBoolean(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.LIST:
                if (Buscar.isNotNull(s)) {
                    return cst.append("(!").append(s.getCodigoGenerado()).append(".isEmpty())");
                } else {
                    return cst.append("Casting.toBoolean(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.MAP:
                if (Buscar.isNotNull(s)) {
                    return cst.append("(!").append(s.getCodigoGenerado()).append(".isEmpty())");
                } else {
                    return cst.append("Casting.toBoolean(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.REF:
                return cst.append("Casting.toBoolean(").append(s.getCodigoGenerado()).append(")");
            case Tipo.BOOLEAN:
                return cst.append(s.getCodigoGenerado());
            case Tipo.INTEGER:
                if (Buscar.isConstante(s)) {
                    return cst.append((Integer.parseInt(s.getCodigoGenerado().toString()) != 0) ? "true" : "false");
                } else if (Buscar.isNotNull(s)) {
                    return cst.append("(").append(s.getCodigoGenerado()).append("!=0)");
                } else {
                    return cst.append("Casting.toBoolean(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.LONG:
                if (Buscar.isConstante(s)) {
                    return cst.append((Long.parseLong(s.getCodigoGenerado().toString()) != 0) ? "true" : "false");
                } else if (Buscar.isNotNull(s)) {
                    return cst.append("(").append(s.getCodigoGenerado()).append("!=0)");
                } else {
                    return cst.append("Casting.toBoolean(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.FLOAT:
                if (Buscar.isConstante(s)) {
                    return cst.append((Float.parseFloat(s.getCodigoGenerado().toString()) != 0) ? "true" : "false");
                } else if (Buscar.isNotNull(s)) {
                    return cst.append("(").append(s.getCodigoGenerado()).append("!=0)");
                } else {
                    return cst.append("Casting.toBoolean(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.DOUBLE:
                if (Buscar.isConstante(s)) {
                    return cst.append((Double.parseDouble(s.getCodigoGenerado().toString()) != 0) ? "true" : "false");
                } else if (Buscar.isNotNull(s)) {
                    return cst.append("(").append(s.getCodigoGenerado()).append("!=0)");
                } else {
                    return cst.append("Casting.toBoolean(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.STRING:
                if (Buscar.isConstante(s)) {
                    String cad = s.getCodigoGenerado().substring(1, s.getCodigoGenerado().length() - 1);
                    return cst.append((cad.isEmpty() || cad.equals("0")) ? "false" : "true");
                } else {
                    return cst.append("Casting.toBoolean(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.FILE:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null)");
            case Tipo.BOX:
                if (Buscar.isNotNull(s)) {
                    return cst.append(s.getCodigoGenerado()).append(".booleanValue()");
                } else {
                    return cst.append("Casting.toBoolean(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.NUMBER:
                if (Buscar.isNotNull(s)) {
                    return cst.append("(").append(s.getCodigoGenerado()).append(".intValue() != 0)");
                } else {
                    return cst.append("Casting.toBoolean(").append(s.getCodigoGenerado()).append(")");
                }
        }
        return null;
    }

    /**
     * Castea una expresión a integer
     *
     * @param s Simbolo
     * @return Casting
     */
    public static StringBuilder toInteger(Simbolo s) {
        if (Buscar.isUndef(s)) {
            return s.getCodigoGenerado();
        }
        s = colToScalar(s);
        StringBuilder cst = new StringBuilder(100);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.ARRAY:
                if (Buscar.isNotNull(s)) {
                    return cst.append(s.getCodigoGenerado()).append(".length");
                } else {
                    return cst.append("Casting.toInteger(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.LIST:
                if (Buscar.isNotNull(s)) {
                    return cst.append(s.getCodigoGenerado()).append(".size()");
                } else {
                    return cst.append("Casting.toInteger(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.MAP:
                if (Buscar.isNotNull(s)) {
                    return cst.append(s.getCodigoGenerado()).append(".size()");
                } else {
                    return cst.append("Casting.toInteger(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.REF:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null ? 1 : 0)");
            case Tipo.BOOLEAN:
                if (Buscar.isNotNull(s)) {
                    return cst.append("(").append(s.getCodigoGenerado()).append(" ? 1 : 0)");
                } else {
                    return cst.append("Casting.toInteger(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.INTEGER:
                return cst.append(s.getCodigoGenerado());
            case Tipo.LONG:
                return cst.append("Casting.toInteger(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FLOAT:
                return cst.append("Casting.toInteger(").append(s.getCodigoGenerado()).append(")");
            case Tipo.DOUBLE:
                return cst.append("Casting.toInteger(").append(s.getCodigoGenerado()).append(")");
            case Tipo.STRING:
                return cst.append("Casting.toInteger(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FILE:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null").append(" ? 1 : 0)");
            case Tipo.BOX:
                if (Buscar.isNotNull(s)) {
                    return cst.append(s.getCodigoGenerado()).append(".intValue()");
                } else {
                    return cst.append("Casting.toInteger(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.NUMBER:
                if (Buscar.isNotNull(s)) {
                    return cst.append(s.getCodigoGenerado()).append(".intValue()");
                } else {
                    return cst.append("Casting.toInteger(").append(s.getCodigoGenerado()).append(")");
                }
        }
        return null;
    }

    /**
     * Castea una expresión a long
     *
     * @param s Simbolo
     * @return Casting
     */
    public static StringBuilder toLong(Simbolo s) {
        if (Buscar.isUndef(s)) {
            return s.getCodigoGenerado();
        }
        s = colToScalar(s);
        StringBuilder cst = new StringBuilder(100);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.ARRAY:
                if (Buscar.isNotNull(s)) {
                    return cst.append("(long)").append(s.getCodigoGenerado()).append(".length");
                } else {
                    return cst.append("Casting.toLong(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.LIST:
                if (Buscar.isNotNull(s)) {
                    return cst.append("(long)").append(s.getCodigoGenerado()).append(".size()");
                } else {
                    return cst.append("Casting.toLong(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.MAP:
                if (Buscar.isNotNull(s)) {
                    return cst.append("(long)").append(s.getCodigoGenerado()).append(".size()");
                } else {
                    return cst.append("Casting.toLong(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.REF:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null ? 1l : 0l)");
            case Tipo.BOOLEAN:
                if (Buscar.isNotNull(s)) {
                    return cst.append("(").append(s.getCodigoGenerado()).append(" ? 1l : 0l)");
                } else {
                    return cst.append("Casting.toLong(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.INTEGER:
                if (Buscar.isConstante(s)) {
                    return cst.append(s.getCodigoGenerado().toString()).append("l");
                } else {
                    return cst.append("Perl.toLong(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.LONG:
                return cst.append(s.getCodigoGenerado());
            case Tipo.FLOAT:
                return cst.append("Casting.toLong(").append(s.getCodigoGenerado()).append(")");
            case Tipo.DOUBLE:
                return cst.append("Casting.toLong(").append(s.getCodigoGenerado()).append(")");
            case Tipo.STRING:
                return cst.append("Casting.toLong(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FILE:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null").append(" ? 1l : 0l)");
            case Tipo.BOX:
                if (Buscar.isNotNull(s)) {
                    return cst.append(s.getCodigoGenerado()).append(".longValue()");
                } else {
                    return cst.append("Casting.toLong(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.NUMBER:
                if (Buscar.isNotNull(s)) {
                    return cst.append(s.getCodigoGenerado()).append(".longValue()");
                } else {
                    return cst.append("Casting.toLong(").append(s.getCodigoGenerado()).append(")");
                }
        }
        return null;
    }

    /**
     * Castea una expresión a float
     *
     * @param s Simbolo
     * @return Casting
     */
    public static StringBuilder toFloat(Simbolo s) {
        if (Buscar.isUndef(s)) {
            return s.getCodigoGenerado();
        }
        s = colToScalar(s);
        StringBuilder cst = new StringBuilder(100);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.ARRAY:
                if (Buscar.isNotNull(s)) {
                    return cst.append("(float)").append(s.getCodigoGenerado()).append(".length");
                } else {
                    return cst.append("Casting.toFloat(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.LIST:
                if (Buscar.isNotNull(s)) {
                    return cst.append("(float)").append(s.getCodigoGenerado()).append(".size()");
                } else {
                    return cst.append("Casting.toFloat(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.MAP:
                if (Buscar.isNotNull(s)) {
                    return cst.append("(float)").append(s.getCodigoGenerado()).append(".size()");
                } else {
                    return cst.append("Casting.toFloat(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.REF:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null ? 1f : 0f)");
            case Tipo.BOOLEAN:
                if (Buscar.isNotNull(s)) {
                    return cst.append("(").append(s.getCodigoGenerado()).append(" ? 1l : 0l)");
                } else {
                    return cst.append("Casting.toFloat(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.INTEGER:
                return cst.append("Casting.toFloat(").append(s.getCodigoGenerado()).append(")");
            case Tipo.LONG:
                return cst.append("Casting.toFloat(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FLOAT:
                return cst.append(s.getCodigoGenerado());
            case Tipo.DOUBLE:
                if (Buscar.isConstante(s)) {
                    return cst.append(s.getCodigoGenerado().toString()).append("f");
                } else {
                    return cst.append("Casting.toFloat(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.STRING:
                return cst.append("Casting.toFloat(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FILE:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null").append(" ? 1f : 0f)");
            case Tipo.BOX:
                if (Buscar.isNotNull(s)) {
                    return cst.append(s.getCodigoGenerado()).append(".floatValue()");
                } else {
                    return cst.append("Casting.toFloat(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.NUMBER:
                if (Buscar.isNotNull(s)) {
                    return cst.append(s.getCodigoGenerado()).append(".floatValue()");
                } else {
                    return cst.append("Casting.toFloat(").append(s.getCodigoGenerado()).append(")");
                }
        }
        return null;
    }

    /**
     * Castea una expresión a double
     *
     * @param s Simbolo
     * @return Casting
     */
    public static StringBuilder toDouble(Simbolo s) {
        if (Buscar.isUndef(s)) {
            return s.getCodigoGenerado();
        }
        s = colToScalar(s);
        StringBuilder cst = new StringBuilder(100);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.ARRAY:
                if (Buscar.isNotNull(s)) {
                    return cst.append("(double)").append(s.getCodigoGenerado()).append(".length");
                } else {
                    return cst.append("Casting.toDouble(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.LIST:
                if (Buscar.isNotNull(s)) {
                    return cst.append("(double)").append(s.getCodigoGenerado()).append(".size()");
                } else {
                    return cst.append("Casting.toDouble(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.MAP:
                if (Buscar.isNotNull(s)) {
                    return cst.append("(double)").append(s.getCodigoGenerado()).append(".size()");
                } else {
                    return cst.append("Casting.toDouble(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.REF:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null ? 1d : 0d");
            case Tipo.BOOLEAN:
                if (Buscar.isNotNull(s)) {
                    return cst.append("(").append(s.getCodigoGenerado()).append(" ? 1d : 0d)");
                } else {
                    return cst.append("Casting.toDouble(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.INTEGER:
                return cst.append("Casting.toDouble(").append(s.getCodigoGenerado()).append(")");
            case Tipo.LONG:
                return cst.append("Casting.toDouble(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FLOAT:
                return cst.append("Casting.toDouble(").append(s.getCodigoGenerado()).append(")");
            case Tipo.DOUBLE:
                return cst.append(s.getCodigoGenerado());
            case Tipo.STRING:
                return cst.append("Casting.toDouble(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FILE:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null").append(" ? 1d : 0d)");
            case Tipo.BOX:
                if (Buscar.isNotNull(s)) {
                    return cst.append(s.getCodigoGenerado()).append(".doubleValue()");
                } else {
                    return cst.append("Casting.toDouble(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.NUMBER:
                if (Buscar.isNotNull(s)) {
                    return cst.append(s.getCodigoGenerado()).append(".doubleValue()");
                } else {
                    return cst.append("Casting.toDouble(").append(s.getCodigoGenerado()).append(")");
                }
        }
        return null;
    }

    /**
     * Castea una expresión a string
     *
     * @param s Simbolo
     * @return Casting
     */
    public static StringBuilder toString(Simbolo s) {
        if (Buscar.isUndef(s)) {
            return s.getCodigoGenerado();
        }
        s = colToScalar(s);
        StringBuilder cst = new StringBuilder(100);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.ARRAY:
                return cst.append("Casting.toString(").append(s.getCodigoGenerado()).append(".length)");
            case Tipo.LIST:
                return cst.append("Casting.toString(").append(s.getCodigoGenerado()).append(".size())");
            case Tipo.MAP:
                return cst.append("Casting.toString(").append(s.getCodigoGenerado()).append(".size())");
            case Tipo.REF:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null ? \"1\" : \"0\")");
            case Tipo.BOOLEAN:
                if (Buscar.isNotNull(s)) {
                    return cst.append("(").append(s.getCodigoGenerado()).append(" ? \"1\" : \"0\")");
                } else {
                    return cst.append("Casting.toString(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.INTEGER:
                return cst.append("Casting.toString(").append(s.getCodigoGenerado()).append(")");
            case Tipo.LONG:
                return cst.append("Casting.toString(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FLOAT:
                return cst.append("Casting.toString(").append(s.getCodigoGenerado()).append(")");
            case Tipo.DOUBLE:
                return cst.append("Casting.toString(").append(s.getCodigoGenerado()).append(")");
            case Tipo.STRING:
                return cst.append(s.getCodigoGenerado());
            case Tipo.FILE:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null").append(" ? \"1\" : \"0\")");
            case Tipo.BOX:
                if (Buscar.isNotNull(s)) {
                    return cst.append(s.getCodigoGenerado()).append(".stringValue()");
                } else {
                    return cst.append("Casting.toString(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.NUMBER:
                if (Buscar.isNotNull(s)) {
                    return cst.append(s.getCodigoGenerado()).append(".toString()");
                } else {
                    return cst.append("Casting.toString(").append(s.getCodigoGenerado()).append(")");
                }
        }
        return null;
    }

    /**
     * Castea una expresión a box
     *
     * @param s Simbolo
     * @return Casting
     */
    public static StringBuilder toBox(Simbolo s) {
        if (Buscar.isUndef(s)) {
            return s.getCodigoGenerado();
        }
        s = colToScalar(s);
        StringBuilder cst = new StringBuilder(100);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.ARRAY:
                return cst.append("Casting.box(").append(s.getCodigoGenerado()).append(".length)");
            case Tipo.LIST:
                return cst.append("Casting.box(").append(s.getCodigoGenerado()).append(".size())");
            case Tipo.MAP:
                return cst.append("Casting.box(").append(s.getCodigoGenerado()).append(".size())");
            case Tipo.REF:
                return cst.append("Casting.box(").append(s.getCodigoGenerado()).append(")");
            case Tipo.BOOLEAN:
                return cst.append("Casting.box(").append(s.getCodigoGenerado()).append(")");
            case Tipo.INTEGER:
                return cst.append("Casting.box(").append(s.getCodigoGenerado()).append(")");
            case Tipo.LONG:
                return cst.append("Casting.box(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FLOAT:
                return cst.append("Casting.box(").append(s.getCodigoGenerado()).append(")");
            case Tipo.DOUBLE:
                return cst.append("Casting.box(").append(s.getCodigoGenerado()).append(")");
            case Tipo.STRING:
                return cst.append("Casting.box(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FILE:
                return cst.append("Casting.box(").append(s.getCodigoGenerado()).append(")");
            case Tipo.BOX:
                return cst.append(s.getCodigoGenerado());
            case Tipo.NUMBER:
                return cst.append("Casting.box(").append(s.getCodigoGenerado()).append(")");
        }
        return null;
    }

    /**
     * Castea una expresión a Number
     *
     * @param s Simbolo
     * @return Casting
     */
    public static StringBuilder toNumber(Simbolo s) {
        if (Buscar.isUndef(s)) {
            return s.getCodigoGenerado();
        }
        s = colToScalar(s);
        StringBuilder cst = new StringBuilder(50);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.ARRAY:
                return cst.append(s.getCodigoGenerado()).append(".length");
            case Tipo.LIST:
                return cst.append(s.getCodigoGenerado()).append(".size()");
            case Tipo.MAP:
                return cst.append(s.getCodigoGenerado()).append(".size()");
            case Tipo.REF:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null ? 1 : 0)");
            case Tipo.BOOLEAN:
                if (Buscar.isNotNull(s)) {
                    return cst.append("(").append(s.getCodigoGenerado()).append(" ? 1 : 0)");
                } else {
                    return cst.append("Casting.toInteger(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.INTEGER:
            case Tipo.LONG:
            case Tipo.FLOAT:
            case Tipo.DOUBLE:
            case Tipo.NUMBER:
                return cst.append(s.getCodigoGenerado());
            case Tipo.STRING:
                return cst.append("Casting.toDouble(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FILE:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null").append(" ? 1 : 0)");
            case Tipo.BOX:
                if (Buscar.isNotNull(s)) {
                    return cst.append(s.getCodigoGenerado()).append(".numberValue()");
                } else {
                    return cst.append("Casting.toNumber(").append(s.getCodigoGenerado()).append(")");
                }
        }
        return null;
    }

    /**
     * Castea una expresión a Referencia
     *
     * @param s Simbolo
     * @param destino Tipo destino
     * @return Casting
     */
    public static StringBuilder toRef(Simbolo s, Tipo destino) {
        if (Buscar.isUndef(s)) {
            return s.getCodigoGenerado();
        }
        s = colToScalar(s);
        StringBuilder cst = new StringBuilder(50);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.REF:
                return cst.append(s.getCodigoGenerado());
            case Tipo.BOX:
                cst.append("((").append(Tipos.declaracion(destino)).append(")");
                if (Buscar.isNotNull(s)) {
                    cst.append(s.getCodigoGenerado()).append(".refValue()");
                } else {
                    cst.append("Casting.toRef(").append(s.getCodigoGenerado()).append(")");
                }
                return cst.append(")");
        }
        return null;
    }

    /**
     * Castea una expresión a Fichero
     *
     * @param s Simbolo
     * @return Casting
     */
    public static StringBuilder toFile(Simbolo s) {
        if (Buscar.isUndef(s)) {
            return s.getCodigoGenerado();
        }
        s = colToScalar(s);
        StringBuilder cst = new StringBuilder(50);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.FILE:
                return cst.append(s.getCodigoGenerado());
            case Tipo.BOX:
                if (Buscar.isNotNull(s)) {
                    return cst.append(s.getCodigoGenerado()).append(".fileValue()");
                } else {
                    return cst.append("Casting.toFile(").append(s.getCodigoGenerado()).append(")");
                }
        }
        return null;
    }

    /**
     * Castea un list a un array del mismo tipo
     *
     * @param s Simbolo
     * @return Casting
     */
    public static StringBuilder toArray(Simbolo s) {
        StringBuilder cst = new StringBuilder(50);
        Tipo array = s.getTipo().getSubtipo(1).add(0, Tipo.ARRAY);
        cst.append(s.getCodigoGenerado()).append(".toArray(").append(Tipos.inicializacion(array, "0")).append(")");
        return cst;
    }

    /**
     * Castea un array a list del mismo tipo
     *
     * @param s Simbolo
     * @return Casting
     */
    public static StringBuilder toList(Simbolo s) {
        StringBuilder cst = new StringBuilder(50);
        Tipo array = s.getTipo().getSubtipo(1).add(0, Tipo.LIST);
        cst.append(Tipos.inicializacion(array, s.getCodigoGenerado().toString()));
        return cst;
    }

    /**
     * Castea una coleccion de Number a Number
     *
     * @param origen Expresion origen
     * @param destino Tipo destino
     * @return Casting
     */
    private static StringBuilder toColNumber(Simbolo origen, Tipo destino) {
        StringBuilder cst = new StringBuilder(200);
        cst.append("((").append(Tipos.declaracion(destino)).append(")");
        if (destino.isList()) {
            cst.append("(PerlList)");
        } else if (destino.isMap()) {
            cst.append("(PerlMap)");
        }
        cst.append(origen.getCodigoGenerado()).append(")");
        return cst;
    }

    /**
     * Castea entre colecciones
     *
     * @param origen Expresion origen
     * @param destino Tipo destino
     * @return Casting
     */
    private static StringBuilder toCol(Simbolo origen, Tipo destino) {
        if (Buscar.isUndef(origen)) {
            return origen.getCodigoGenerado();
        }
        //Si las colecciones son numericas y el casting es a Number
        if (origen.getTipo().getSimple().isNumberType() && destino.getSimple().isNumber()) {
            Tipo t = origen.getTipo().getSubtipo(0);
            t.setSimple(Tipo.NUMBER);
            if (t.equals(destino)) {
                return toColNumber(origen, destino);
            }
        }
        StringBuilder cst = new StringBuilder(200);
        StringBuilder tipo = Tipos.declaracion(origen.getTipo());
        cst.append("(").append(Tipos.declaracion(destino)).append(")");
        cst.append("Casting.cast(new Casting() { ");
        cst.append("@Override ");
        cst.append("public Object casting(Object arg) {");
        cst.append("if(arg==null){return null;}");
        String tam;
        if (origen.getTipo().isArray()) {
            tam = "col.length";
        } else if (origen.getTipo().isList()) {
            tam = "col.size()";
        } else {
            tam = "col.size()*2";
        }
        if (!destino.isArray()) {
            tam += "*2";
        }
        cst.append(tipo).append(" col =").append("(").append(tipo).append(")").append("arg;");
        cst.append(Tipos.declaracion(destino)).append(" ").append(" col2=").append(Tipos.inicializacion(destino, tam)).append(";");
        if (origen.getTipo().isMap()) {
            fromMap(cst, origen.getTipo(), destino);
        } else {
            fromArrayList(cst, origen.getTipo(), destino);
        }
        cst.append("return col2;");
        cst.append("}},").append(origen.getCodigoGenerado()).append(")");
        return cst;
    }

    /**
     * Convierte desde un array a lista
     *
     * @param cst Codigo para el casting
     * @param origen Expresion origen
     * @param destino Tipo destino
     */
    private static void fromArrayList(StringBuilder cst, Tipo origen, Tipo destino) {
        String lectura;
        String tam;
        if (origen.isArray()) {
            lectura = "col[i]";
            tam = "col.length";
        } else {
            lectura = "col.get(i)";
            tam = "col.size()";
        }
        if (destino.isMap()) {
            cst.append("String key = null;");
            cst.append("Boolean value = false;");
            cst.append("if(").append(tam).append("%2!=0){throw new RuntimeException();}");
        }
        cst.append("for(int i=0;i<").append(tam).append(";i++){");
        Terminal t = new Terminal(null);
        t.setCodigoGenerado(new StringBuilder(lectura));
        t.setTipo(origen.getSubtipo(1));
        if (destino.isArray()) {
            cst.append("col2[i]=").append(casting(t, destino.getSubtipo(1))).append(";");
        } else if (destino.isList()) {
            cst.append("col2.add(").append(casting(t, destino.getSubtipo(1))).append(");");
        } else {
            cst.append("if(value){");
            cst.append("col2.put(key,").append(casting(t, destino.getSubtipo(1))).append(");");
            cst.append("}else{");
            cst.append("key = ").append(toString(t)).append(";");
            cst.append("}");
            cst.append("value = !value").append(";");
        }
        cst.append("}");
    }

    /**
     * Convierte desde un Mapa
     *
     * @param cst Codigo para el casting
     * @param origen Expresion origen
     * @param destino Tipo destino
     */
    private static void fromMap(StringBuilder cst, Tipo origen, Tipo destino) {
        Tipo subtipo = destino.getSubtipo(1);
        cst.append("java.util.Iterator<PerlMap.Entry<String,").append(Tipos.declaracion(subtipo)).append(">>");
        cst.append(" ").append("it = col.entrySet().iterator();");
        cst.append("int i=0;");
        cst.append("while(it.hasNext()){");
        cst.append("PerlMap.Entry<String,").append(Tipos.declaracion(subtipo)).append("> ").append("entry = it.next();");
        Terminal t1 = new Terminal(null);
        t1.setCodigoGenerado(new StringBuilder("entry.getKey()"));
        t1.setTipo(new Tipo(Tipo.STRING));
        Terminal t2 = new Terminal(null);
        t2.setCodigoGenerado(new StringBuilder("entry.getValue()"));
        t2.setTipo(origen.getSubtipo(1));
        if (destino.isArray()) {
            cst.append("col2[i++]=").append(casting(t1, subtipo)).append(";");;
            cst.append("col2[i++]=").append(casting(t2, subtipo)).append(";");;
        } else if (destino.isList()) {
            cst.append("col2.add(").append(casting(t1, subtipo)).append(");");
            cst.append("col2.add(").append(casting(t2, subtipo)).append(");");
        } else {
            cst.append("col2.put(").append(t1.getCodigoGenerado()).append(",").append(casting(t2, subtipo)).append(");");
        }
        cst.append("}");
    }

    /**
     * Evita que un valor no sea nulo siempre que no se trate de una colección
     *
     * @param s Simbolo
     * @return Codigo no nulo
     */
    public static StringBuilder checkNull(Simbolo s) {
        return checkNull(s, !Buscar.isNotNull(s));
    }

    /**
     * Evita que un valor no sea nulo siempre que no se trate de una colección
     *
     * @param s Simbolo
     * @param isNull Codigo es nulo
     * @return Codigo no nulo
     */
    public static StringBuilder checkNull(Simbolo s, boolean isNull) {
        if (isNull) {
            return new StringBuilder(100).append("Pd.checkNull(").append(s.getCodigoGenerado()).append(")");
        }
        return s.getCodigoGenerado();
    }

    /**
     * Castea la expresion origen al destino pudiendo filtrar nulos
     *
     * @param origen Expresion origen
     * @param destino Tipo destino
     * @param notNull Comprobar no nulos
     * @return Casting
     */
    public static StringBuilder casting(Simbolo origen, Tipo destino, boolean notNull) {
        if (notNull) {
            return castingNotNull(origen, destino);
        } else {
            return casting(origen, destino);
        }
    }

    /**
     * Castea la expresion origen al destino y luego comprueba que no sea nula (Solo tipos numericos y cadenas)
     *
     * @param origen Expresion origen
     * @param destino Tipo destino
     * @return Casting
     */
    public static StringBuilder castingNotNull(Simbolo origen, Tipo destino) {
        StringBuilder codigo = casting(origen, destino);
        if ((destino.isSimple() && !destino.isFile()) && !Buscar.isNotNull(origen)) {
            return new StringBuilder(100).append("Pd.checkNull(").append(codigo).append(")");
        }
        return codigo;
    }

    /**
     * Castea la expresion origen al destino
     *
     * @param origen Expresion origen
     * @param destino Tipo destino
     * @return Casting
     */
    public static StringBuilder casting(Simbolo origen, Tipo destino) {
        switch (destino.getTipo(0)) {
            case Tipo.BOOLEAN:
                return toBoolean(origen);
            case Tipo.INTEGER:
                return toInteger(origen);
            case Tipo.LONG:
                return toLong(origen);
            case Tipo.FLOAT:
                return toFloat(origen);
            case Tipo.DOUBLE:
                return toDouble(origen);
            case Tipo.STRING:
                return toString(origen);
            case Tipo.FILE:
                return toFile(origen);
            case Tipo.BOX:
                return toBox(origen);
            case Tipo.NUMBER:
                return toNumber(origen);
            case Tipo.REF:
                return toRef(origen, destino);
            default:
                if (!origen.getTipo().equals(destino)) {
                    if (origen.getTipo().isArrayOrList() && destino.isArrayOrList()
                            && origen.getTipo().getSubtipo(1).equals(destino.getSubtipo(1))) {
                        if (destino.isArray()) {
                            return toArray(origen);
                        } else {
                            return toList(origen);
                        }
                    }
                    return toCol(origen, destino);
                }
        }
        return new StringBuilder(origen.getCodigoGenerado());
    }

}
