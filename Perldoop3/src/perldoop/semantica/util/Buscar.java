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
}
