package perldoop.lib;

import perldoop.lib.box.NumberBox;
import perldoop.lib.box.RefBox;
import perldoop.lib.box.StringBox;

/**
 * TEMPORAL hasta definir clases de funciones auxiliares
 *
 * @author César Pomar
 */
public class Perl {

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

}
