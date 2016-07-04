package perldoop.semantica.util;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.lexico.Token;

/**
 * Clase para hacer busquedas en el arbol
 *
 * @author César Pomar
 */
public final class Buscar {

    /**
     * Obtiene el primer token del simbolo
     *
     * @param s Simbolo
     * @return Token
     */
    public static Token tokenInicio(Simbolo s) {
        Simbolo hijo = null;
        do {
            Simbolo[] hijos = s.getHijos();
            if (hijos.length == 0) {
                Simbolo padre = hijo.getPadre();
                while (padre.getHijos().length < 2) {
                    padre = padre.getPadre();
                }
                hijo = padre.getHijos()[1];
            }
        } while (!(hijo instanceof Terminal));
        return ((Terminal) hijo).getToken();
    }

    /**
     * Obtiene el ultimo token del simbolo
     *
     * @param s Simbolo
     * @return TOken
     */
    public static Token tokenFin(Simbolo s) {
        Simbolo hijo = null;
        do {
            Simbolo[] hijos = s.getHijos();
            if (hijos.length == 0) {
                return null;
            }
            hijo = hijos[hijos.length - 1];
        } while (!(hijo instanceof Terminal));
        return ((Terminal) hijo).getToken();
    }

    /**
     * Busca un padre por su clase
     *
     * @param <T> Tipo del padre a buscar
     * @param s Simbolo que busca padre
     * @param clase Clase del padre a buscar
     * @return Si el padre existe lo retorna, en caso contrario null
     */
    public static <T extends Simbolo> T buscarPadre(Simbolo s, Class<T> clase) {
        Simbolo padre = s.getPadre();
        while (padre != null) {
            if (padre.getClass().isAssignableFrom(clase)) {
                return (T) padre;
            }
            padre = padre.getPadre();
        }
        return null;
    }

    /**
     * obtiene el padre n superior del simbolo s
     *
     * @param s Simbolo que busca padre
     * @param n Numero padre a recorrer, siendo 0 su padre inmediato
     * @return Padre n
     */
    public static Simbolo getPadre(Simbolo s, int n) {
        Simbolo padre = s.getPadre();
        for (int i = 0; padre != null && i < n; i++) {
            padre = padre.getPadre();
        }
        return padre;
    }

}
