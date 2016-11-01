package perldoop.lib;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;
import perldoop.lib.util.Union;

/**
 * Clase para las acciones de perldoop
 *
 * @author César Pomar
 */
public final class Pd {

    /**
     * Crea una union para concatenar
     *
     * @return Union
     */
    public static Union union() {
        return new Union();
    }

    /**
     * Accede a varias posiciones dentro de un array
     *
     * @param <T> Tipo de los elementos
     * @param array Array
     * @param indexs Posiciones
     * @return Subarray
     */
    public static <T> T[] access(T[] array, Number[] indexs) {
        T[] res = (T[]) Array.newInstance(array.getClass().getComponentType(), indexs.length);
        for (int i = 0; i < indexs.length; i++) {
            res[i] = array[indexs[i].intValue()];
        }
        return res;
    }

    /**
     * Accede a varias posiciones dentro de una lista
     *
     * @param <T> Tipo de los elementos
     * @param lista Lista
     * @param indexs Posiciones
     * @return Sublista
     */
    public static <T> PerlList<T> access(PerlList<T> lista, Number[] indexs) {
        PerlList<T> res = new PerlList<>(indexs.length);
        for (Number n : indexs) {
            res.add(lista.get(n.intValue()));
        }
        return res;
    }

    /**
     * Accede a varias posiciones dentro de una map
     *
     * @param <T> Tipo de los elementos
     * @param map Mapa
     * @param keys Array de claves
     * @return Array de valores
     */
    public static <T> PerlList<T> access(PerlMap<T> map, String[] keys) {
        PerlList res = new PerlList(keys.length);
        for (String key : keys) {
            res.add(map.get(key));
        }
        return res;
    }

    /**
     * Accede a varias posiciones dentro de un array y las actualiza con los nuevos valores
     *
     * @param <T> Tipo de los elementos
     * @param array Array
     * @param indexs Posiciones
     * @param values Valores
     * @return Numero asignaciones
     */
    public static <T> Integer access(T[] array, Number[] indexs, T[] values) {
        int i;
        for (i = 0; i < indexs.length && i < values.length; i++) {
            array[indexs[i].intValue()] = values[i];
        }
        return i;
    }

    /**
     * Accede a varias posiciones dentro de una lista y las actualiza con los nuevos valores
     *
     * @param <T> Tipo de los elementos
     * @param lista Lista
     * @param indexs Posiciones
     * @param values Valores
     * @return Numero asignaciones
     */
    public static <T> Integer access(PerlList<T> lista, Number[] indexs, PerlList<T> values) {
        int i;
        for (i = 0; i < indexs.length && i < values.size(); i++) {
            lista.set(indexs[i].intValue(), values.get(i));
        }
        return i;
    }

    /**
     * Accede a varias posiciones dentro de una map
     *
     * @param <T> Tipo de los elementos
     * @param map Mapa
     * @param keys Array de claves
     * @param values Valores
     * @return Numero asignaciones
     */
    public static <T> Integer access(PerlMap<T> map, String[] keys, PerlList<T> values) {
        int i;
        for (i = 0; i < keys.length && i < values.size(); i++) {
            map.put(keys[i], values.get(i));
        }
        return i;
    }

    /**
     * Retorna el ultimo elemento
     *
     * @param <T> Tipo de elementos
     * @param array Array
     * @return Ultimo elemento
     */
    public static <T> T last(T[] array) {
        return array[array.length - 1];
    }

    /**
     * Retorna el ultimo elemento
     *
     * @param <T> Tipo de elementos
     * @param list Lista
     * @return Ultimo elemento
     */
    public static <T> T last(PerlList<T> list) {
        return list.get(list.size() - 1);
    }

    /**
     * Retorna el primer elemento
     *
     * @param <T> Tipo de elementos
     * @param array Array
     * @return Primer elemento
     */
    public static <T> T first(T[] array) {
        return array[0];
    }

    /**
     * Retorna el primer elemento
     *
     * @param <T> Tipo de elementos
     * @param list Lista
     * @return Primer elemento
     */
    public static <T> T first(PerlList<T> list) {
        return list.get(0);
    }

    /**
     * Crea una copia superficial del array
     *
     * @param <T> Tipo de los elementos del array
     * @param array Array
     * @return Copia superficial
     */
    public static <T> T[] copy(T[] array) {
        return Arrays.copyOf(array, array.length);
    }

    /**
     * Crea una copia superficial de la lista
     *
     * @param <T> Tipo de los elementos de la lista
     * @param list Lista
     * @return Copia superficial
     */
    public static <T> PerlList<T> copy(PerlList<T> list) {
        return new PerlList(list);
    }

    /**
     * Crea una copia superficial del map
     *
     * @param <T> Tipo de los elementos del map
     * @param map Map
     * @return Copia superficial
     */
    public static <T> PerlMap<T> copy(PerlMap<T> map) {
        return new PerlMap<>(map);
    }

    /**
     * Ejecuta un comando en el shell del sistema
     *
     * @param cmd Comando
     * @return Salida del comando
     */
    public static String cmd(String cmd) {
        try {
            Process exec = Runtime.getRuntime().exec(cmd);
            exec.waitFor();
            Scanner s = new Scanner(exec.getInputStream()).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } catch (IOException | InterruptedException ex) {
            return "";
        }
    }

    /**
     * Compara dos cadenas
     *
     * @param str1 Cadena 1
     * @param str2 Cadena 2
     * @return 0 si str1 == str2, 1 si str1 '-&gt' str2, -1 si str1 '-&lt' str1
     */
    public static int compare(String str1, String str2) {
        return str1.compareTo(str2) % 2;
    }

    /**
     * Compara dos numeros
     *
     * @param n1 Numero 1
     * @param n2 Numero 2
     * @return 0 si n1 == n2, 1 si n1 '-&gt' n2, -1 si n1 '-&lt' n1
     */
    public static int compare(Number n1, Number n2) {
        return Double.compare(n1.doubleValue(), n2.doubleValue()) % 2;
    }

    /**
     * Comprueba que el Boolean no sea null y si lo es lo evalua como false
     *
     * @param bool Boolean
     * @return Boolean no nulo
     */
    public static Boolean checkNull(Boolean bool) {
        return bool == null ? false : bool;
    }

    /**
     * Comprueba que el Boolean no sea null y si lo es retorna el valor por defecto
     *
     * @param <T> Tipo de numero
     * @param n Numero
     * @return Numero no nulo
     */
    public static <T extends Number> T checkNull(T n) {
        return (T) (n == null ? 0 : n);
    }

    /**
     * Comprueba que el Boolean no sea null y si lo es retorna el valor por defecto
     *
     * @param str String
     * @return String no nulo
     */
    public static String checkNull(String str) {
        return str == null ? "" : str;
    }

    /**
     * Calcula la exponencia de dos numeros
     *
     * @param n Numero
     * @param exp Exponente
     * @return n elevado a exp
     */
    public static Double pow(Number n, Number exp) {
        return Math.pow(n.doubleValue(), exp.doubleValue());
    }

    /**
     * Repite la cadena str n veces
     *
     * @param str Cadena
     * @param n Numero de repeticiones
     * @return Cadena repetida
     */
    public static String x(String str, Number n) {
        int nrep = n.intValue();
        if (nrep < 1) {
            return "";
        }
        StringBuilder cadena = new StringBuilder(str.length() * nrep + 1);
        for (int i = 0; i < nrep; i++) {
            cadena.append(str);
        }
        return cadena.toString();
    }

    /**
     * Calcula el modulo de dos numeros
     *
     * @param n Numero
     * @param mod Modulo
     * @return n elevado a exp
     */
    public static Integer mod(Integer n, Integer mod) {
        return Math.floorMod(n, mod);
    }

    /**
     * lee una linea de file
     *
     * @param file Fichero
     * @return linea
     */
    public static String read(PerlFile file) {
        if (file == null) {
            return "";
        }
        return file.read();
    }

    /**
     * Lee todas las lineas de file
     *
     * @param file Fichero
     * @return Array de lineas
     */
    public static String[] readLines(PerlFile file) {
        if (file == null) {
            return new String[0];
        }
        return file.readLines();
    }

    /**
     * Evalua una expresion dentro de una función para poder ser aceptada como sentencia
     *
     * @param exp Expresión
     */
    public static void eval(Object exp) {
    }

    /**
     * Reliza la operacion xor
     *
     * @param exp1 Expresion 1
     * @param exp2 Expresion 2
     * @return !exp1 && exp2 || exp1 && !exp2
     */
    public static Boolean xor(Boolean exp1, Boolean exp2) {
        return !exp1 && exp2 || exp1 && !exp2;
    }

    /**
     * Función para realizar multiasignaciones en expresiones
     *
     * @param n Numero de valores
     * @param aux Asginaciones auxiliares
     * @param eqs Asignaciones
     * @return Asignaciones
     */
    public static Box[] equals(Integer n, Object[] aux, Box... eqs) {
        return eqs;
    }

    /**
     * Función para realizar multiasignaciones en expresiones
     *
     * @param n Numero de valores
     * @param aux Asignaciones auxiliares
     * @param eqs Asignaciones
     * @return Numero de valores
     */
    public static Integer sequals(Integer n, Object[] aux, Box... eqs) {
        return n;
    }

    /**
     * Función para realizar multiasignaciones en expresiones
     *
     * @param n Numero de valores
     * @param eqs Asignaciones
     * @return Asignaciones
     */
    public static Box[] equals(Integer n, Box... eqs) {
        return eqs;
    }

    /**
     * Función para realizar multiasignaciones en expresiones
     *
     * @param n Numero de valores
     * @param eqs Asignaciones
     * @return Numero de valores
     */
    public static Integer sequals(Integer n, Box... eqs) {
        return n;
    }

}
