package perldoop.lib;

/**
 * Clase para la ejecución de expresiones regulares
 *
 * @author César Pomar
 */
public final class Regex {

    /**
     * Comprueba si una cadena comple la expresión regular
     *
     * @param srt Cadena
     * @param regex Expresión regular
     * @param mods Modificadores
     * @param global Busca todas las coincidencias
     * @return Evaluacion de la expresion regular sobre la cadena
     */
    public static boolean simpleMatch(String srt, String regex, String mods, boolean global) {
        return true;
    }

    /**
     * Comprueba si una cadena comple la expresión regular
     *
     * @param srt Cadena
     * @param regex Expresión regular
     * @param mods Modificadores
     * @param global Busca todas las coincidencias
     * @return Coincidencias de las expresiones entre parentesis
     */
    public static String[] match(String srt, String regex, String mods, boolean global) {
        return null;
    }

    /**
     * Realiza una substitucion de todas las coincidencias en una cadena
     *
     * @param srt Cadena
     * @param regex Expresión regular
     * @param subs Cadena de remplazo
     * @param mods Modificadores
     * @param global Busca todas las coincidencias
     * @return Cadena actualizada
     */
    public static String substitution(String srt, String regex, String subs, String mods, boolean global) {
        return null;
    }

    /**
     * Realiza una translacion de caracteres por un remplazo
     *
     * @param srt Cadena
     * @param regex Expresión regular
     * @param subs Caracteres de remplazo
     * @param mods Modificadores
     * @return Cadena actualizada
     */
    public static String translation(String srt, String regex, String subs, String mods) {
        return null;
    }

}
