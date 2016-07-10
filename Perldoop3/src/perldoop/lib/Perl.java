package perldoop.lib;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import perldoop.lib.box.*;

/**
 * TEMPORAL hasta definir clases de funciones auxiliares
 *
 * @author César Pomar
 */
public class Perl {

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

    public static <T> T[] access(T[] array, Number... indexs) {
        T[] res = (T[]) Array.newInstance(array.getClass().getComponentType(), indexs.length);
        for (int i = 0; i < indexs.length; i++) {
            res[i] = array[indexs[i].intValue()];
        }
        return res;
    }

    public static <T> PerlList<T> access(PerlList<T> lista, Number... indexs) {
        PerlList<T> res = new PerlList<>(indexs.length);
        for (Number n : indexs) {
            res.add(lista.get(n.intValue()));
        }
        return res;
    }

    public static <T> PerlList<T> union(List<T>... cols) {
        int len = 0;
        PerlList<T> lista;
        for (List l : cols) {
            len += l.size();
        }
        lista = new PerlList<>(len * 2);
        for (List l : cols) {
            lista.addAll(l);
        }
        return lista;
    }

    public static <T> List<T> part(T... elems) {
        return Arrays.asList(elems);
    }

    public static Integer multiE(Object... eqs) {
        return eqs.length;
    }

}
