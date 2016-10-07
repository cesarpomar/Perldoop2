package perldoop.lib;

import java.lang.reflect.Array;
import java.util.Arrays;
import perldoop.lib.util.Union;

/**
 * Clase para las acciones de perldoop
 *
 * @author César Pomar
 */
public class Pd {

    /**
     * Crea una union para concatenar
     *
     * @return Union
     */
    public static Union union() {
        return new Union();
    }

    /**
     * Función para realizar asignaciones en sus argumentos
     *
     * @param eqs Asignaciones
     * @return Numero de asignaciones
     */
    public static Integer equals(Object... eqs) {
        return eqs.length;
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

}
