package perldoop.lib;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * TEMPORAL hasta definir clases de funciones auxiliares
 *
 * @author César Pomar
 */
public final class Perl {

    /**
     * Imprime por pantalla
     *
     * @param args Argumentos
     * @return Retorna 1 si tiene exito
     */
    public static int print(Object... args) {
        for (Object arg : args) {
            System.out.print(arg);
        }
        return 1;
    }

    /**
     * Imprime en un fichero
     *
     * @param f Fichero
     * @param args Argumentos
     * @return Retorna 1 si tiene exito
     */
    public static int print(PerlFile f, Object... args) {
        if (f == null) {
            return 0;
        }
        return f.print(args);
    }

    /**
     * Procesa la cadena str y elimina y retorna su ultimo caracter
     *
     * @param str Cadena a procesar
     * @param rn Retorno de la funcion
     * @return Actualizacion de variable
     */
    public static String chop(String str, Ref<String>... rn) {
        int len = str.length();
        if (len == 0) {//Cadena vacia
            if (rn.length > 0) {
                rn[0].set("");
            }
            return str;
        }
        if (rn.length > 0) {//Ultimo caracter
            rn[0].set("" + str.charAt(len - 1));
        }
        return str.substring(0, len - 1);
    }

    /**
     * Procesa la cadena str y elimina y retorna el numero de caracteres eliminados
     *
     * @param str Cadena a procesar
     * @param rn Retorno de la funcion
     * @return Actualizacion de variable
     */
    public static String chomp(String str, Ref<Integer>... rn) {
        String chomp = str.trim();
        if (rn.length > 0) {
            rn[0].set(str.length() - chomp.length());
        }
        return chomp;
    }

    /**
     * Ordena un array cuyos elementos son comparables
     *
     * @param <T> Tipo de los elementos
     * @param array Array
     * @param comp Comparador personalizado
     * @return Array ordenado
     */
    public static <T extends Comparable> T[] sort(T[] array, Comparator<T>... comp) {
        T[] copia = Arrays.copyOf(array, array.length);
        if (comp.length > 0) {
            Arrays.sort(copia, comp[0]);
        } else if (array.length > 1) {
            if (array[0] instanceof Number) {//Number no es comparable
                Arrays.sort((Number[]) copia, (Number n1, Number n2) -> Casting.toString(n1).compareTo(Casting.toString(n2)));
            } else {
                Arrays.sort(copia);
            }
        }
        return copia;
    }

    /**
     * Ordena una lista cuyos elementos son comparables
     *
     * @param <T> Tipo de los elementos
     * @param list Lista
     * @param comp Comparador personalizado
     * @return Array ordenado
     */
    public static <T extends Comparable> PerlList<T> sort(PerlList<T> list, Comparator<T>... comp) {
        PerlList copia = new PerlList(list);
        if (comp.length > 0) {
            copia.sort(comp[0]);
        } else if (!list.isEmpty()) {
            if (list.get(0) instanceof Number) {//Number no es comparable
                ((PerlList<Number>) copia).sort((Number n1, Number n2) -> Casting.toString(n1).compareTo(Casting.toString(n2)));
            } else {
                copia.sort(null);
            }
        }
        return copia;
    }
    
    

}
