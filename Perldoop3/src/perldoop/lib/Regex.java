package perldoop.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jregex.MatchResult;
import jregex.Matcher;
import jregex.Pattern;
import jregex.PerlSubstitution;
import jregex.Replacer;
import jregex.TextBuffer;
import org.jtr.transliterate.CharacterParseException;
import org.jtr.transliterate.CharacterParser;
import org.jtr.transliterate.CharacterReplacer;

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
        return new Pattern(regex, mods).matches(srt);
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
        Pattern pattern = new Pattern(regex, mods);
        Matcher matcher = pattern.matcher(srt);
        if (global) {
            List<String> list = new ArrayList<>(100);
            while (matcher.find()) {
                String[] groups = matcher.groups();
                list.addAll(Arrays.asList(groups).subList(1, groups.length));
            }
            return list.toArray(new String[list.size()]);
        } else if (matcher.find()) {
            String[] groups = matcher.groups();
            return Arrays.copyOfRange(groups, 1, groups.length);
        }
        return new String[0];
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
        Pattern pattern = new Pattern(regex, mods);
        if (global) {
            Replacer replacer = pattern.replacer(subs);
            return replacer.replace(srt);
        } else {
            Replacer replacer = pattern.replacer(new FirstPerlSubstitution(srt));
            return replacer.replace(srt);
        }
    }

    /**
     * Realiza una translacion de caracteres por un remplazo
     *
     * @param srt Cadena
     * @param source Caracteres originales
     * @param dest Caracteres de remplazo
     * @param mods Modificadores
     * @return Cadena actualizada
     */
    public static String translation(String srt, String source, String dest, String mods) {
        try {
            CharacterReplacer replacer = new CharacterReplacer(source, dest);
            int flags = 0;
            for (char flag : mods.toCharArray()) {
                switch (flag) {
                    case 'c':
                        flags = flags | CharacterParser.COMPLEMENT_MASK;
                        break;
                    case 'd':
                        flags = flags | CharacterParser.DELETE_UNREPLACEABLES_MASK;
                        break;
                    case 's':
                        flags = flags | CharacterParser.SQUASH_DUPLICATES_MASK;
                        break;
                }
            }
            replacer.setFlags(flags);
            return replacer.doReplacement(srt);
        } catch (CharacterParseException ex) {
            return "";
        }
    }

    /**
     * Clase auxiliar para remplazar solo la primera coincidencia
     */
    private static class FirstPerlSubstitution extends PerlSubstitution {

        private boolean first = true;

        public FirstPerlSubstitution(String string) {
            super(string);
        }

        @Override
        public void appendSubstitution(MatchResult match, TextBuffer dest) {
            if (first) {
                super.appendSubstitution(match, dest);
                first = false;
            } else {
                dest.append(match.toString());
            }
        }

    }

}
