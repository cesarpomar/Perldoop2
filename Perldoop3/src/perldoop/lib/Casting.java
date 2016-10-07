package perldoop.lib;

import perldoop.lib.box.BooleanBox;
import perldoop.lib.box.EmptyBox;
import perldoop.lib.box.FileBox;
import perldoop.lib.box.NumberBox;
import perldoop.lib.box.RefBox;
import perldoop.lib.box.StringBox;

/**
 * Intefaz para implementar los castings
 *
 * @author César Pomar
 */
public abstract class Casting {

    /**
     * Función donde se implementa el casting entre colecciones
     *
     * @param arg Colección a convertir
     * @return Colección resultante
     */
    public abstract Object casting(Object arg);

    /**
     * Función para realizar el casting entre coleciones
     *
     * @param c Case con la implementación del casting
     * @param col Colección a convertir
     * @return Colección resultante
     */
    public static Object cast(Casting c, Object col) {
        return c.casting(col);
    }

    /**
     * Convierte el argumento a boolean
     *
     * @param array Array
     * @return Boolean
     */
    public static Boolean toBoolean(Object[] array) {
        return array == null ? null : array.length > 0;
    }

    /**
     * Convierte el argumento a boolean
     *
     * @param list Lista
     * @return Boolean
     */
    public static Boolean toBoolean(PerlList list) {
        return list == null ? null : !list.isEmpty();
    }

    /**
     * Convierte el argumento a boolean
     *
     * @param map Map
     * @return Boolean
     */
    public static Boolean toBoolean(PerlMap map) {
        return map == null ? null : !map.isEmpty();
    }

    /**
     * Convierte el argumento a boolean
     *
     * @param n Numero
     * @return Boolean
     */
    public static Boolean toBoolean(Number n) {
        return n == null ? null : n.intValue() != 0;
    }

    /**
     * Convierte el argumento a boolean
     *
     * @param s String
     * @return Boolean
     */
    public static Boolean toBoolean(String s) {
        return s == null ? null : !s.isEmpty() && !s.contains("0");
    }

    /**
     * Convierte el argumento a boolean
     *
     * @param box Box
     * @return Boolean
     */
    public static Boolean toBoolean(Box box) {
        return box == null ? null : toBoolean(box.stringValue());
    }

    /**
     * Convierte el argumento a integer
     *
     * @param array Array
     * @return Integer
     */
    public static Integer toInteger(Object[] array) {
        return array == null ? null : array.length;
    }

    /**
     * Convierte el argumento a integer
     *
     * @param list Lista
     * @return Integer
     */
    public static Integer toInteger(PerlList list) {
        return list == null ? null : list.size();
    }

    /**
     * Convierte el argumento a integer
     *
     * @param map Map
     * @return Integer
     */
    public static Integer toInteger(PerlMap map) {
        return map == null ? null : map.size();
    }

    /**
     * Convierte el argumento a integer
     *
     * @param bool boolean
     * @return Integer
     */
    public static Integer toInteger(Boolean bool) {
        return bool == null ? null : bool ? 1 : 0;
    }

    /**
     * Convierte el argumento a integer
     *
     * @param n Numero
     * @return Integer
     */
    public static Integer toInteger(Number n) {
        return n.intValue();
    }

    /**
     * Convierte el argumento a integer
     *
     * @param s String
     * @return Integer
     */
    public static Integer toInteger(String s) {
        return s == null ? null : Integer.parseInt(s);
    }

    /**
     * Convierte el argumento a integer
     *
     * @param box Box
     * @return Integer
     */
    public static Integer toInteger(Box box) {
        return box == null ? null : box.intValue();
    }

    /**
     * Convierte el argumento a long
     *
     * @param array Array
     * @return Long
     */
    public static Long toLong(Object[] array) {
        return array == null ? null : (long) array.length;
    }

    /**
     * Convierte el argumento a long
     *
     * @param list Lista
     * @return Long
     */
    public static Long toLong(PerlList list) {
        return list == null ? null : (long) list.size();
    }

    /**
     * Convierte el argumento a long
     *
     * @param map Map
     * @return Long
     */
    public static Long toLong(PerlMap map) {
        return map == null ? null : (long) map.size();
    }

    /**
     * Convierte el argumento a long
     *
     * @param bool boolean
     * @return Long
     */
    public static Long toLong(Boolean bool) {
        return bool == null ? null : bool ? 1l : 0l;
    }

    /**
     * Convierte el argumento a long
     *
     * @param n Numero
     * @return Long
     */
    public static Long toLong(Number n) {
        return n == null ? null : n.longValue();
    }

    /**
     * Convierte el argumento a long
     *
     * @param s String
     * @return Long
     */
    public static Long toLong(String s) {
        return s == null ? null : Long.parseLong(s);
    }

    /**
     * Convierte el argumento a long
     *
     * @param box Box
     * @return Long
     */
    public static Long toLong(Box box) {
        return box == null ? null : box.longValue();
    }

    /**
     * Convierte el argumento a float
     *
     * @param array Array
     * @return Float
     */
    public static Float toFloat(Object[] array) {
        return array == null ? null : (float) array.length;
    }

    /**
     * Convierte el argumento a float
     *
     * @param list Lista
     * @return Float
     */
    public static Float toFloat(PerlList list) {
        return list == null ? null : (float) list.size();
    }

    /**
     * Convierte el argumento a float
     *
     * @param map Map
     * @return Float
     */
    public static Float toFloat(PerlMap map) {
        return map == null ? null : (float) map.size();
    }

    /**
     * Convierte el argumento a float
     *
     * @param bool boolean
     * @return Float
     */
    public static Float toFloat(Boolean bool) {
        return bool == null ? null : bool ? 1f : 0f;
    }

    /**
     * Convierte el argumento a float
     *
     * @param n Numero
     * @return Float
     */
    public static Float toFloat(Number n) {
        return n == null ? null : n.floatValue();
    }

    /**
     * Convierte el argumento a float
     *
     * @param s String
     * @return Float
     */
    public static Float toFloat(String s) {
        return s == null ? null : Float.parseFloat(s);
    }

    /**
     * Convierte el argumento a float
     *
     * @param box Box
     * @return Float
     */
    public static Float toFloat(Box box) {
        return box == null ? null : box.floatValue();
    }

    /**
     * Convierte el argumento a double
     *
     * @param array Array
     * @return Double
     */
    public static Double toDouble(Object[] array) {
        return array == null ? null : (double) array.length;
    }

    /**
     * Convierte el argumento a double
     *
     * @param list Lista
     * @return Double
     */
    public static Double toDouble(PerlList list) {
        return list == null ? null : (double) list.size();
    }

    /**
     * Convierte el argumento a double
     *
     * @param map Map
     * @return Double
     */
    public static Double toDouble(PerlMap map) {
        return map == null ? null : (double) map.size();
    }

    /**
     * Convierte el argumento a double
     *
     * @param bool boolean
     * @return Double
     */
    public static Double toDouble(Boolean bool) {
        return bool == null ? null : bool ? 1d : 0d;
    }

    /**
     * Convierte el argumento a double
     *
     * @param n Numero
     * @return Double
     */
    public static Double toDouble(Number n) {
        return n == null ? null : n.doubleValue();
    }

    /**
     * Convierte el argumento a double
     *
     * @param s String
     * @return Double
     */
    public static Double toDouble(String s) {
        return s == null ? null : Double.parseDouble(s);
    }

    /**
     * Convierte el argumento a double
     *
     * @param box Box
     * @return Double
     */
    public static Double toDouble(Box box) {
        return box == null ? null : box.doubleValue();
    }

    /**
     * Convierte el argumento a number
     *
     * @param box Box
     * @return Number
     */
    public static Number toNumber(Box box) {
        return box == null ? null : box.numberValue();
    }

    /**
     * Convierte el argumento a string
     *
     * @param array Array
     * @return String
     */
    public static String toString(Object[] array) {
        return array == null ? null : String.valueOf(array.length);
    }

    /**
     * Convierte el argumento a string
     *
     * @param list Lista
     * @return String
     */
    public static String toString(PerlList list) {
        return list == null ? null : String.valueOf(list.size());
    }

    /**
     * Convierte el argumento a string
     *
     * @param map Map
     * @return String
     */
    public static String toString(PerlMap map) {
        return map == null ? null : String.valueOf(map.size());
    }

    /**
     * Convierte el argumento a string
     *
     * @param bool boolean
     * @return String
     */
    public static String toString(Boolean bool) {
        return bool == null ? null : bool ? "1" : "0";
    }

    /**
     * Convierte el argumento a string
     *
     * @param n Numero
     * @return String
     */
    public static String toString(Number n) {
        return n == null ? null : n.toString();
    }

    /**
     * Convierte el argumento a string
     *
     * @param box Box
     * @return String
     */
    public static String toString(Box box) {
        return box == null ? null : box.stringValue();
    }

    /**
     * Crea un box vacio
     *
     * @return Box
     */
    public static Box box() {
        return new EmptyBox();
    }

    /**
     * Crea un box de booleans
     *
     * @param b Boolean
     * @return Box
     */
    public static Box box(Boolean b) {
        return new BooleanBox(b);
    }

    /**
     * Crea un box de numeros
     *
     * @param n Numero
     * @return Box
     */
    public static Box box(Number n) {
        return new NumberBox(n);
    }

    /**
     * Crea un box de cadenas
     *
     * @param s String
     * @return Box
     */
    public static Box box(String s) {
        return new StringBox(s);
    }

    /**
     * Crea un box de referencias
     *
     * @param ref Referencia
     * @return Box
     */
    public static Box box(Ref ref) {
        return new RefBox(ref);
    }

    /**
     * Crea un box de ficheros
     *
     * @param file Referencia
     * @return Box
     */
    public static Box box(PerlFile file) {
        return new FileBox(file);
    }

    /**
     * Convierte el argumento a ref
     *
     * @param box Box
     * @return Ref
     */
    public static Ref toRef(Box box) {
        return box == null ? null : box.refValue();
    }

    /**
     * Convierte el argumento a file
     *
     * @param box Box
     * @return File
     */
    public static PerlFile toFile(Box box) {
        return box == null ? null : box.fileValue();
    }

}
