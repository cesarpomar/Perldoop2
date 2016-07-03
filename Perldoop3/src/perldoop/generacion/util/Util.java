package perldoop.generacion.util;

import perldoop.modelo.arbol.Simbolo;

/**
 *
 * @author César
 */
public class Util {

    public static void limpiar(Simbolo s) {
        for (Simbolo s2 : s.getHijos()) {
            s2.setCodigoGenerado(null);
        }

    }

}
