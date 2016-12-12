package perldoop.generacion.util;

import java.util.Arrays;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.SimboloAux;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.ParserEtiquetas;

/**
 * Clase para conversiones de tipo Hadoop y Java
 *
 * @author César Pomar
 */
public final class Hadoop {

    /**
     * Convierte una expresion Java a un tipo Hadoop
     *
     * @param s Expresion java
     * @param tipo Tipo Hadoop
     * @return Conversion
     */
    public static StringBuilder casting(Simbolo s, Tipo tipo) {
        switch (tipo.getTipo(0)) {
            case Tipo.BOOLEAN:
                return new StringBuilder("new BooleanWritable(" + Casting.toBoolean(s) + ")");
            case Tipo.INTEGER:
                return new StringBuilder("new IntWritable(" + Casting.toInteger(s) + ")");
            case Tipo.LONG:
                return new StringBuilder("new LongWritable(" + Casting.toLong(s) + ")");
            case Tipo.FLOAT:
                return new StringBuilder("new FloatWritable(" + Casting.toFloat(s) + ")");
            case Tipo.DOUBLE:
                return new StringBuilder("new DoubleWritable(" + Casting.toDouble(s) + ")");
            case Tipo.STRING:
                return new StringBuilder("new Text(" + Casting.toString(s) + ")");
            default:
                return null;
        }
    }

    /**
     * Convierte una variable Hadoop a su tipo basico de java
     *
     * @param variable Variable Hadoop
     * @param tipo Tipo Hadoop
     * @return Simbolo con tipo java
     */
    public static Simbolo casting(String variable, Tipo tipo) {
        StringBuilder codigo = new StringBuilder(variable);
        Simbolo s = new SimboloAux(tipo, codigo);
        switch (tipo.getTipo(0)) {
            case Tipo.BOOLEAN:
                codigo.append(".get()");
            case Tipo.INTEGER:
                codigo.append(".get()");
            case Tipo.LONG:
                codigo.append(".get()");
            case Tipo.FLOAT:
                codigo.append(".get()");
            case Tipo.DOUBLE:
                codigo.append(".get()");
            case Tipo.STRING:
                codigo.append(".toString()");
        }
        return s;
    }

    /**
     * Obtiene el tipo Hadoop de una etiqueta Java
     *
     * @param token Token de tipo
     * @return Tipo Hadoop
     */
    public static String hadoop(Token token) {
        if (token == null) {//Por defecto son cadenas
            return "Text";
        }
        Tipo tipo = ParserEtiquetas.parseTipo(Arrays.asList(token));
        switch (tipo.getTipo(0)) {
            case Tipo.BOOLEAN:
                return "BooleanWritable";
            case Tipo.INTEGER:
                return "IntWritable";
            case Tipo.LONG:
                return "LongWritable";
            case Tipo.FLOAT:
                return "FloatWritable";
            case Tipo.DOUBLE:
                return "DoubleWritable";
            case Tipo.STRING:
                return "Text";
        }
        return null;
    }
}
