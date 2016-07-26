package perldoop.generacion.util;

import java.util.Iterator;
import java.util.Map;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.semantica.Tipo;

/**
 * Clase para generacion de casting
 *
 * @author César Pomar
 */
public final class Casting {

    /**
     * Castea una expresión a boolean
     *
     * @param s Simbolo
     * @return Casting
     */
    public static StringBuilder toBoolean(Simbolo s) {
        StringBuilder cst = new StringBuilder(50);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.ARRAY:
                return cst.append(s.getCodigoGenerado()).append(".length > 0");
            case Tipo.LIST:
                return cst.append("!").append(s.getCodigoGenerado()).append(".isEmpty()");
            case Tipo.MAP:
                return cst.append("!").append(s.getCodigoGenerado()).append(".isEmpty()");
            case Tipo.REF:
                return cst.append(s.getCodigoGenerado()).append(" != null");
            case Tipo.BOOLEAN:
                return cst.append(s.getCodigoGenerado());
            case Tipo.INTEGER:
                if (s.getTipo().isConstante()) {
                    return cst.append((Integer.parseInt(s.getCodigoGenerado().toString()) != 0) ? "true" : "false");
                } else {
                    return cst.append("Perl.toBoolean(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.LONG:
                if (s.getTipo().isConstante()) {
                    return cst.append((Long.parseLong(s.getCodigoGenerado().toString()) != 0) ? "true" : "false");
                } else {
                    return cst.append("Perl.toBoolean(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.FLOAT:
                if (s.getTipo().isConstante()) {
                    return cst.append((Float.parseFloat(s.getCodigoGenerado().toString()) != 0) ? "true" : "false");
                } else {
                    return cst.append("Perl.toBoolean(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.DOUBLE:
                if (s.getTipo().isConstante()) {
                    return cst.append((Double.parseDouble(s.getCodigoGenerado().toString()) != 0) ? "true" : "false");
                } else {
                    return cst.append("Perl.toBoolean(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.STRING:
                if (s.getTipo().isConstante()) {
                    String cad = s.getCodigoGenerado().substring(1, s.getCodigoGenerado().length() - 1);
                    return cst.append((cad.isEmpty() || cad.equals("0")) ? "false" : "true");
                } else {
                    return cst.append("Perl.toBoolean(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.FILE:
                return cst.append(s.getCodigoGenerado()).append(" != null");
            case Tipo.BOX:
                return cst.append(s.getCodigoGenerado()).append(".booleanValue()");
            case Tipo.NUMBER:
                return cst.append(s.getCodigoGenerado()).append(".intValue() != 0");
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
        StringBuilder cst = new StringBuilder(50);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.ARRAY:
                return cst.append(s.getCodigoGenerado()).append(".length");
            case Tipo.LIST:
                return cst.append(s.getCodigoGenerado()).append(".size()");
            case Tipo.MAP:
                return cst.append(s.getCodigoGenerado()).append(".size()");
            case Tipo.REF:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null) ? 1 : 0");
            case Tipo.BOOLEAN:
                return cst.append("(Perl.toBoolean(").append(s.getCodigoGenerado()).append(")) ? 1 : 0");
            case Tipo.INTEGER:
                return cst.append(s.getCodigoGenerado());
            case Tipo.LONG:
                if (s.getTipo().isConstante()) {
                    return cst.append(s.getCodigoGenerado().toString()).append("l");
                } else {
                    return cst.append("Perl.toInteger(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.FLOAT:
                if (s.getTipo().isConstante()) {
                    return cst.append(s.getCodigoGenerado().toString()).append("f");
                } else {
                    return cst.append("Perl.toInteger(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.DOUBLE:
                if (s.getTipo().isConstante()) {
                    return cst.append(s.getCodigoGenerado().toString()).append("d");
                } else {
                    return cst.append("Perl.toInteger(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.STRING:
                return cst.append("Integer.parse(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FILE:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null").append(") ? 1 : 0");
            case Tipo.BOX:
                return cst.append(s.getCodigoGenerado()).append(".intvalue()");
            case Tipo.NUMBER:
                return cst.append(s.getCodigoGenerado()).append(".intValue()");
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
        StringBuilder cst = new StringBuilder(50);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.ARRAY:
                return cst.append(s.getCodigoGenerado()).append(".length");
            case Tipo.LIST:
                return cst.append(s.getCodigoGenerado()).append(".size()");
            case Tipo.MAP:
                return cst.append(s.getCodigoGenerado()).append(".size()");
            case Tipo.REF:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null) ? 1l : 0l");
            case Tipo.BOOLEAN:
                return cst.append("(Perl.toBoolean(").append(s.getCodigoGenerado()).append(")) ? 1l : 0l");
            case Tipo.INTEGER:
                return cst.append("Perl.toLong(").append(s.getCodigoGenerado()).append(")");
            case Tipo.LONG:
                return cst.append(s.getCodigoGenerado());
            case Tipo.FLOAT:
                return cst.append("Perl.toLong(").append(s.getCodigoGenerado()).append(")");
            case Tipo.DOUBLE:
                return cst.append("Perl.toLong(").append(s.getCodigoGenerado()).append(")");
            case Tipo.STRING:
                return cst.append("Long.parse(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FILE:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null").append(") ? 1l : 0l");
            case Tipo.BOX:
                return cst.append(s.getCodigoGenerado()).append(".longValue()");
            case Tipo.NUMBER:
                return cst.append(s.getCodigoGenerado()).append(".longValue()");

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
        StringBuilder cst = new StringBuilder(50);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.ARRAY:
                return cst.append(s.getCodigoGenerado()).append(".length");
            case Tipo.LIST:
                return cst.append(s.getCodigoGenerado()).append(".size()");
            case Tipo.MAP:
                return cst.append(s.getCodigoGenerado()).append(".size()");
            case Tipo.REF:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null) ? 1f : 0f");
            case Tipo.BOOLEAN:
                return cst.append("(Perl.toBoolean(").append(s.getCodigoGenerado()).append(")) ? 1f : 0f");
            case Tipo.INTEGER:
                return cst.append("Perl.toFloat(").append(s.getCodigoGenerado()).append(")");
            case Tipo.LONG:
                return cst.append("Perl.toFloat(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FLOAT:
                return cst.append(s.getCodigoGenerado());
            case Tipo.DOUBLE:
                if (s.getTipo().isConstante()) {
                    return cst.append(s.getCodigoGenerado().toString()).append("f");
                } else {
                    return cst.append("Perl.toFloat(").append(s.getCodigoGenerado()).append(")");
                }
            case Tipo.STRING:
                return cst.append("Float.parse(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FILE:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null").append(") ? 1f : 0f");
            case Tipo.BOX:
                return cst.append(s.getCodigoGenerado()).append(".floatValue()");
            case Tipo.NUMBER:
                return cst.append(s.getCodigoGenerado()).append(".floatValue()");
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
        StringBuilder cst = new StringBuilder(50);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.ARRAY:
                return cst.append(s.getCodigoGenerado()).append(".length");
            case Tipo.LIST:
                return cst.append(s.getCodigoGenerado()).append(".size()");
            case Tipo.MAP:
                return cst.append(s.getCodigoGenerado()).append(".size()");
            case Tipo.REF:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null) ? 1d : 0d");
            case Tipo.BOOLEAN:
                return cst.append("(Perl.toBoolean(").append(s.getCodigoGenerado()).append(")) ? 1d : 0d");
            case Tipo.INTEGER:
                return cst.append("Perl.toDouble(").append(s.getCodigoGenerado()).append(")");
            case Tipo.LONG:
                return cst.append("Perl.toDouble(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FLOAT:
                return cst.append("Perl.toDouble(").append(s.getCodigoGenerado()).append(")");
            case Tipo.DOUBLE:
                return cst.append(s.getCodigoGenerado());
            case Tipo.STRING:
                return cst.append("Double.parse(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FILE:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null").append(") ? 1d : 0d");
            case Tipo.BOX:
                return cst.append(s.getCodigoGenerado()).append(".doubleValue()");
            case Tipo.NUMBER:
                return cst.append(s.getCodigoGenerado()).append(".doubleValue()");
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
        StringBuilder cst = new StringBuilder(50);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.ARRAY:
                return cst.append("String.valueOf(").append(s.getCodigoGenerado()).append(".length)");
            case Tipo.LIST:
                return cst.append("String.valueOf(").append(s.getCodigoGenerado()).append(".size())");
            case Tipo.MAP:
                return cst.append("String.valueOf(").append(s.getCodigoGenerado()).append(".size())");
            case Tipo.REF:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null) ? \"1\" : \"0\"");
            case Tipo.BOOLEAN:
                return cst.append("(Perl.toBoolean(").append(s.getCodigoGenerado()).append(")) ? \"1\" : \"0\"");
            case Tipo.INTEGER:
                return cst.append("String.valueOf(").append(s.getCodigoGenerado()).append(")");
            case Tipo.LONG:
                return cst.append("String.valueOf(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FLOAT:
                return cst.append("String.valueOf(").append(s.getCodigoGenerado()).append(")");
            case Tipo.DOUBLE:
                return cst.append("String.valueOf(").append(s.getCodigoGenerado()).append(")");
            case Tipo.STRING:
                return cst.append(s.getCodigoGenerado());
            case Tipo.FILE:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null").append(") ? \"1\" : \"0\"");
            case Tipo.BOX:
                return cst.append(s.getCodigoGenerado()).append(".toString()");
            case Tipo.NUMBER:
                return cst.append(s.getCodigoGenerado()).append(".toString()");
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
        StringBuilder cst = new StringBuilder(50);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.ARRAY:
                return cst.append("Perl.box(").append(s.getCodigoGenerado()).append(".length)");
            case Tipo.LIST:
                return cst.append("Perl.box(").append(s.getCodigoGenerado()).append(".size())");
            case Tipo.MAP:
                return cst.append("Perl.box(").append(s.getCodigoGenerado()).append(".size())");
            case Tipo.REF:
                return cst.append("Perl.box(").append(s.getCodigoGenerado()).append(")");
            case Tipo.BOOLEAN:
                return cst.append("Perl.box(").append(s.getCodigoGenerado()).append(")");
            case Tipo.INTEGER:
                return cst.append("Perl.box(").append(s.getCodigoGenerado()).append(")");
            case Tipo.LONG:
                return cst.append("Perl.box(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FLOAT:
                return cst.append("Perl.box(").append(s.getCodigoGenerado()).append(")");
            case Tipo.DOUBLE:
                return cst.append("Perl.box(").append(s.getCodigoGenerado()).append(")");
            case Tipo.STRING:
                return cst.append("Perl.box(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FILE:
                return cst.append("Perl.box(").append(s.getCodigoGenerado()).append(")");
            case Tipo.BOX:
                return cst.append(s.getCodigoGenerado());
            case Tipo.NUMBER:
                return cst.append("Perl.box(").append(s.getCodigoGenerado()).append(")");
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
        StringBuilder cst = new StringBuilder(50);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.ARRAY:
                return cst.append(s.getCodigoGenerado()).append(".length");
            case Tipo.LIST:
                return cst.append(s.getCodigoGenerado()).append(".size()");
            case Tipo.MAP:
                return cst.append(s.getCodigoGenerado()).append(".size()");
            case Tipo.REF:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null) ? 1d : 0d");
            case Tipo.BOOLEAN:
                return cst.append("Perl.toBoolean(").append(s.getCodigoGenerado()).append(") ? 1d : 0d");
            case Tipo.INTEGER:
            case Tipo.LONG:
            case Tipo.FLOAT:
            case Tipo.DOUBLE:
            case Tipo.NUMBER:
                return cst.append(s.getCodigoGenerado());
            case Tipo.STRING:
                return cst.append("Double.parse(").append(s.getCodigoGenerado()).append(")");
            case Tipo.FILE:
                return cst.append("(").append(s.getCodigoGenerado()).append(" != null").append(") ? 1d : 0d");
            case Tipo.BOX:
                return cst.append(s.getCodigoGenerado()).append(".doubleValue()");
        }
        return null;
    }

    /**
     * Castea una expresión a Referencia
     *
     * @param s Simbolo
     * @return Casting
     */
    public static StringBuilder toRef(Simbolo s) {
        StringBuilder cst = new StringBuilder(50);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.REF:
                return cst.append(s.getCodigoGenerado());
            case Tipo.BOX:
                return cst.append(s.getCodigoGenerado()).append(".refValue()");
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
        StringBuilder cst = new StringBuilder(50);
        switch (s.getTipo().getTipo().get(0)) {
            case Tipo.FILE:
                return cst.append(s.getCodigoGenerado());
            case Tipo.BOX:
                return cst.append(s.getCodigoGenerado()).append(".fileValue()");
        }
        return null;
    }

    /**
     * Castea entre colecciones
     *
     * @param origen Expresion origen
     * @param destino Tipo destino
     * @return Casting
     */
    public static StringBuilder toCol(Simbolo origen, Tipo destino) {
        StringBuilder cst = new StringBuilder(200);
        cst.append("Pd.cast(new Casting() {");
        cst.append("@Override");
        cst.append("public <T> T casting(Object col) {");
        String tam;
        if (origen.getTipo().isArray()) {
            tam = "col.length";
        } else {
            tam = "col.size()";
        }
        if (!destino.isArray()) {
            tam += "*3";
        }
        cst.append(Tipos.declaracion(destino)).append(" ").append(" col2=").append(Tipos.inicializacion(destino, tam));
        if (origen.getTipo().isMap()) {
            fromMap(cst, origen.getTipo(), destino);
        } else {
            fromArrayList(cst, origen.getTipo(), destino);
        }
        cst.append("return (T)col2;");
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
        StringBuilder tipo = Tipos.declaracion(destino);
        cst.append(tipo).append(" col =").append("(").append(tipo).append(")").append("arg;");
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
        }
        cst.append("for(int i=0;i<").append(tam).append(";i++){");
        Terminal t = new Terminal(null);
        t.setCodigoGenerado(new StringBuilder(lectura));
        t.setTipo(origen.getSubtipo(1));
        if (destino.isArray()) {
            cst.append("col[i]=").append(casting(t, destino.getSubtipo(1)));
        } else if (destino.isList()) {
            cst.append("col.add(").append(casting(t, destino.getSubtipo(1))).append(")");
        } else {
            cst.append("if(value){");
            cst.append("col.put(key,").append(casting(t, destino.getSubtipo(1))).append(")");
            cst.append("}else{");
            cst.append("key = ").append(toString(t));
            cst.append("}");
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
        cst.append("java.uti.Iterator<PerlMap.Entry<String,").append(Tipos.declaracion(destino)).append(">>");
        cst.append(" ").append("it;");
    }

    /**
     * Castea la expresion origen al destino
     *
     * @param origen Expresion origen
     * @param destino Tipo destino
     * @return Casting
     */
    public static StringBuilder casting(Simbolo origen, Tipo destino) {
        switch (destino.getTipo().get(0)) {
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
                return toRef(origen);
            default:
                if (!origen.getTipo().equals(destino)) {
                    return toCol(origen, destino);
                }
        }
        return new StringBuilder(origen.getCodigoGenerado());
    }

}
