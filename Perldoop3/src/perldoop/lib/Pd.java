package perldoop.lib;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import perldoop.lib.util.Union;

/**
 * Clase para las acciones de perldoop
 *
 * @author César Pomar
 */
public final class Pd {

    /**
     * Argumentos pasados en la ejecución del programa
     */
    public static String[] ARGV = new String[0];

    /**
     * Crea una union para concatenar
     *
     * @return Union
     */
    public static Union union() {
        return new Union();
    }

    /**
     * Accede a varias posiciones dentro de un array en contexto escalar
     *
     * @param <T> Tipo de los elementos
     * @param array Array
     * @param indexs Posiciones
     * @return Elemento del ultimo indexs
     */
    public static <T> T sAccess(T[] array, Number[] indexs) {
        return array[indexs[indexs.length - 1].intValue()];
    }

    /**
     * Accede a varias posiciones dentro de un array y las actualiza
     *
     * @param <T> Tipo de los elementos
     * @param array Array
     * @param indexs Posiciones
     * @param value Valor
     * @return Elemento del ultimo indexs
     */
    public static <T> T sAccess(T[] array, Number[] indexs, T value) {
        return array[indexs[indexs.length - 1].intValue()] = value;
    }

    /**
     * Genera una lista temporal desde un array sin recorrerlo
     *
     * @param <T> Tipo de los elementos
     * @param array Arrays
     * @return Lista temporal
     */
    public static <T> List<T> tList(T... array) {
        return Arrays.asList(array);
    }

    /**
     * Accede a varias posiciones dentro de un array en contexto array
     *
     * @param <T> Tipo de los elementos
     * @param array Array
     * @param indexs Posiciones
     * @return Array de posciones de los indices
     */
    public static <T> T[] aAccess(T[] array, Number[] indexs) {
        T[] res = (T[]) Array.newInstance(array.getClass().getComponentType(), indexs.length);
        for (int i = 0; i < indexs.length; i++) {
            res[i] = array[indexs[i].intValue()];
        }
        return res;
    }

    /**
     * Accede a varias posiciones dentro de un array y las actualiza en el contexto array
     *
     * @param <T> Tipo de los elementos
     * @param array Array
     * @param indexs Posiciones
     * @param values Valores
     * @return Array de posciones de los indices
     */
    public static <T> T[] aAccess(T[] array, Number[] indexs, T... values) {
        T[] res = (T[]) Array.newInstance(array.getClass().getComponentType(), indexs.length);
        for (int i = 0; i < indexs.length && i < values.length; i++) {
            res[i] = array[indexs[i].intValue()] = values[i];
        }
        return res;
    }

    /**
     * Accede a varias posiciones dentro de una lista en el contexto hash
     *
     * @param <T> Tipo de los elementos
     * @param array Array
     * @param indexs Posiciones
     * @param f Funcion para transformar los elementos a box
     * @return Lista de index valor consecutivos
     */
    public static <T> Box[] hAccess(T[] array, Number[] indexs, Function<T, Box> f) {
        Box[] res = new Box[indexs.length * 2];
        for (int i = 0; i < indexs.length; i++) {
            res[i] = Casting.box(indexs[i]);
            res[i + 1] = f.apply(array[indexs[i].intValue()]);
        }
        return res;
    }

    /**
     * Accede a varias posiciones dentro de una lista en contexto escalar
     *
     * @param <T> Tipo de los elementos
     * @param list Lista
     * @param indexs Posiciones
     * @return Elemento del ultimo indexs
     */
    public static <T> T sAccess(List<T> list, Number[] indexs) {
        return list.get(indexs[indexs.length - 1].intValue());
    }

    /**
     * Accede a varias posiciones dentro de una lista y las actualiza
     *
     * @param <T> Tipo de los elementos
     * @param list Lista
     * @param indexs Posiciones
     * @param value Valor
     * @return Elemento del ultimo indexs
     */
    public static <T> T sAccess(List<T> list, Number[] indexs, T value) {
        return list.set(indexs[indexs.length - 1].intValue(), value);
    }

    /**
     * Accede a varias posiciones dentro de una lista en contexto array
     *
     * @param <T> Tipo de los elementos
     * @param list Lista
     * @param indexs Posiciones
     * @return Lista de posiciones de los indices
     */
    public static <T> PerlList<T> aAccess(List<T> list, Number[] indexs) {
        PerlList<T> res = new PerlList<>(indexs.length);
        for (int i = 0; i < indexs.length; i++) {
            res.set(i, list.get(indexs[i].intValue()));
        }
        return res;
    }

    /**
     * Accede a varias posiciones dentro de una lista y las actualiza en el contexto array
     *
     * @param <T> Tipo de los elementos
     * @param list Lista
     * @param indexs Posiciones
     * @param values Valores
     * @return Lista de posiciones de los indices
     */
    public static <T> PerlList<T> aAccess(List<T> list, Number[] indexs, List<T> values) {
        PerlList<T> res = new PerlList<>(indexs.length);
        for (int i = 0; i < indexs.length && i < values.size(); i++) {
            res.set(i, list.set(indexs[i].intValue(), values.get(i)));
        }
        return res;
    }

    /**
     * Accede a varias posiciones dentro de una lista en el contexto hash
     *
     * @param <T> Tipo de los elementos
     * @param list Lista
     * @param indexs Posiciones
     * @param f Funcion para transformar los elementos a box
     * @return Lista de index valor consecutivos
     */
    public static <T> PerlList<Box> hAccess(PerlList<T> list, Number[] indexs, Function<T, Box> f) {
        PerlList<Box> res = new PerlList<>(indexs.length * 2);
        for (int i = 0; i < indexs.length; i++) {
            res.add(Casting.box(indexs[i]));
            res.add(f.apply(list.get(indexs[i].intValue())));
        }
        return res;
    }

    /**
     * Accede a varias posiciones dentro de un hash en contexto escalar
     *
     * @param <T> Tipo de los elementos
     * @param map PerlMap
     * @param keys Claves
     * @return Valor de la ultima clave
     */
    public static <T> T sAccess(PerlMap<T> map, String[] keys) {
        return map.get(keys[keys.length - 1]);
    }

    /**
     * Accede a varias posiciones dentro de una lista y las actualiza
     *
     * @param <T> Tipo de los elementos
     * @param map PerlMap
     * @param keys Claves
     * @param value Valor
     * @return Valor de la ultima clave
     */
    public static <T> T sAccess(PerlMap<T> map, String[] keys, T value) {
        return map.put(Arrays.asList(keys), value);
    }

    /**
     * Accede a varias posiciones dentro de una lista en contexto array
     *
     * @param <T> Tipo de los elementos
     * @param map PerlMap
     * @param keys Claves
     * @return Lista de valores de las claves
     */
    public static <T> PerlList<T> aAccess(PerlMap<T> map, String[] keys) {
        PerlList<T> res = new PerlList<>(keys.length);
        for (int i = 0; i < keys.length; i++) {
            res.set(i, map.get(keys[i]));
        }
        return res;
    }

    /**
     * Accede a varias posiciones dentro de una lista y las actualiza en el contexto array
     *
     * @param <T> Tipo de los elementos
     * @param map PerlMap
     * @param keys Claves
     * @param values Valores
     * @return Lista de valores de las claves
     */
    public static <T> PerlList<T> aAccess(PerlMap<T> map, String[] keys, List<T> values) {
        PerlList<T> res = new PerlList<>(keys.length);
        int i;
        for (i = 0; i < keys.length && i < values.size(); i++) {
            res.set(i, map.put(keys[i], values.get(i)));
        }
        for (; i < keys.length; i++) {
            map.put(keys[i], null);
        }
        return res;
    }

    /**
     * Accede a varias posiciones dentro de una lista en el contexto hash
     *
     * @param <T> Tipo de los elementos
     * @param map PerlMap
     * @param keys Claves
     * @param f Funcion para transformar los elementos a box
     * @return Lista de index valor consecutivos
     */
    public static <T> PerlList<Box> hAccess(PerlMap<T> map, String[] keys, Function<T, Box> f) {
        PerlList<Box> res = new PerlList<>(keys.length * 2);
        for (int i = 0; i < keys.length; i++) {
            res.add(Casting.box(keys[i]));
            res.add(f.apply(map.get(keys[i])));
        }
        return res;
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
     * @return 0 si str1 == str2, 1 si str1 '-&gt;' str2, -1 si str1 '-&lt;' str1
     */
    public static int compare(String str1, String str2) {
        return str1.compareTo(str2) % 2;
    }

    /**
     * Compara dos numeros
     *
     * @param n1 Numero 1
     * @param n2 Numero 2
     * @return 0 si n1 == n2, 1 si n1 '-&gt;' n2, -1 si n1 '-&lt;' n1
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
     * Lee una linea de file
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
     * Lee una linea de la entrada estandar
     *
     * @return linea
     */
    public static String read() {
        return read(PerlFile.STDIN);
    }

    /**
     * Lee todas las lineas de la entrada estandar
     *
     * @return Array de lineas
     */
    public static String[] readLines() {
        return readLines(PerlFile.STDIN);
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
     * @return !exp1 &amp;&amp; exp2 || exp1 &amp;&amp; !exp2
     */
    public static Boolean xor(Boolean exp1, Boolean exp2) {
        return !exp1 && exp2 || exp1 && !exp2;
    }

    /**
     * Función para realizar multiasignaciones en expresiones
     *
     * @param eqs Asignaciones
     * @param n Numero de valores
     * @param aux Asginaciones auxiliares
     * @return Asignaciones
     */
    public static Box[] equals(Object[] aux, Integer n, Box[] eqs) {
        return eqs;
    }

    /**
     * Función para realizar multiasignaciones en expresiones
     *
     * @param eqs Asignaciones
     * @param n Numero de valores
     * @param aux Asignaciones auxiliares
     * @return Numero de valores
     */
    public static Integer sequals(Object[] aux, Integer n, Box[] eqs) {
        return n;
    }

    /**
     * Función para realizar multiasignaciones en expresiones
     *
     * @param n Numero de valores
     * @param eqs Asignaciones
     * @return Asignaciones
     */
    public static Box[] equals(Integer n, Box[] eqs) {
        return eqs;
    }

    /**
     * Función para realizar multiasignaciones en expresiones
     *
     * @param n Numero de valores
     * @param eqs Asignaciones
     * @return Numero de valores
     */
    public static Integer sequals(Integer n, Box[] eqs) {
        return n;
    }

    /**
     * Función para realizar la asignacion del resto de una coleccion
     *
     * @param <T> Tipo de la coleccion
     * @param n Posicion de incio
     * @param array Array de toda la asignacion
     * @return Coleccion asignada
     */
    public static <T> T[] subEquals(Integer n, T[] array) {
        if (n > array.length) {
            return null;
        }
        return Arrays.copyOfRange(array, n, array.length);
    }

    /**
     * Función para realizar la asignacion del resto de una coleccion
     *
     * @param <T> Tipo de la coleccion
     * @param n Posicion de incio
     * @param list Lista de toda la asignacion
     * @return Coleccion asignada
     */
    public static <T> PerlList<T> subEquals(Integer n, PerlList<T> list) {
        if (n > list.size()) {
            return null;
        }
        return new PerlList<>(list.subList(n, list.size()));
    }

    /**
     * Funcion para obtener el retorno de una funcion desde una variable y retornarlo simulando la misma
     *
     * @param <T> Tipo del retorno
     * @param f Ejecucion de la funcion nativa invocada
     * @param rn Argumento retorno de la funcion nativa
     * @return Retorno de la funcion nativa
     */
    public static <T> T rn(Object f, Ref<T> rn) {
        return rn.get();
    }
    
}
