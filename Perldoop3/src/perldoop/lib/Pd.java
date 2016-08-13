package perldoop.lib;

import perldoop.lib.box.BooleanBox;
import perldoop.lib.box.FileBox;
import perldoop.lib.box.NumberBox;
import perldoop.lib.box.RefBox;
import perldoop.lib.box.StringBox;
import perldoop.lib.util.Union;

/**
 * Clase para las acciones de perldoop
 *
 * @author César Pomar
 */
public class Pd {

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
     * Crea una union para concatenar
     *
     * @return Union
     */
    public static Union union() {
        return new Union();
    }

    /**
     * Función para realizar el casting entre coleciónes
     *
     * @param c Case con la implementación del casting
     * @param col Colección a convertir
     * @return Colección resultante
     */
    public static Object cast(Casting c, Object col) {
        return c.casting(col);
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
}
