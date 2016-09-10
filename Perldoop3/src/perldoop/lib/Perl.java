package perldoop.lib;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * TEMPORAL hasta definir clases de funciones auxiliares
 *
 * @author César Pomar
 */
public final class Perl {

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

    public static <T> T[] union(Class<T[]> clase, List<T>... cols) {
        int len = 0;
        T[] array;
        for (List l : cols) {
            len += l.size();
        }
        array = (T[]) Array.newInstance(clase.getComponentType(), len);
        int p = 0;
        for (int i = 0; i < cols.length; i++) {
            for (int j = 0; j < cols[i].size(); j++) {
                array[p++] = cols[i].get(j);
            }
        }
        return array;
    }

    public static void eval(Object exp) {
        //No tiene que hacer nada
    }
    


}
