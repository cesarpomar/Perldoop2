package perldoop.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jregex.MatchResult;
import jregex.Matcher;
import jregex.Pattern;
import jregex.PerlSubstitution;
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

    private static Map<String, Pattern> regexCache = new ConcurrentHashMap<>(1000);
    private static Map<String, PerlSubstitution> substCache = new ConcurrentHashMap<>(1000);

    /**
     * Comprueba si una cadena comple la expresión regular
     *
     * @param str Cadena
     * @param regex Expresión regular
     * @param mods Modificadores
     * @param global Busca todas las coincidencias
     * @return Evaluacion de la expresion regular sobre la cadena
     */
    public static boolean match(String str, String regex, String mods, boolean global) {
        if (str == null) {
            return false;
        }
        String key = regex + "_" + mods;
        Pattern pattern = regexCache.get(key);
        if (pattern == null) {
            pattern = new Pattern(regex, mods);
            regexCache.put(key, pattern);
        }
        return pattern.matcher(str).find();
    }

    /**
     * Obtiene las expresiones entre parentesis dentro de una expresion regular
     *
     * @param str Cadena
     * @param regex Expresión regular
     * @param mods Modificadores
     * @param global Busca todas las coincidencias
     * @return Coincidencias de las expresiones entre parentesis
     */
    public static String[] matcher(String str, String regex, String mods, boolean global) {
        String key = regex + "_" + mods;
        Pattern pattern = regexCache.get(key);
        if (pattern == null) {
            pattern = new Pattern(regex, mods);
            regexCache.put(key, pattern);
        }
        Matcher matcher = pattern.matcher(str);
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
     * @param str Cadena
     * @param regex Expresión regular
     * @param subs Cadena de remplazo
     * @param mods Modificadores
     * @param global Busca todas las coincidencias
     * @return Cadena actualizada
     */
    public static String substitution(String str, String regex, String subs, String mods, boolean global) {
        String key = regex + "_" + mods;
        Pattern pattern = regexCache.get(key);
        if (pattern == null) {
            pattern = new Pattern(regex, mods);
            regexCache.put(key, pattern);
        }
        key = subs + "_" + global;
        PerlSubstitution subst = substCache.get(key);
        if(subst==null){
            if(global){
                subst = new PerlSubstitution(subs);
            }else{
                subst = new FirstPerlSubstitution(subs);
            }
            substCache.put(key, subst);            
        }
        return pattern.replacer(subst).replace(str);
    }

    /**
     * Realiza una translacion de caracteres por un remplazo
     *
     * @param str Cadena
     * @param source Caracteres originales
     * @param dest Caracteres de remplazo
     * @param mods Modificadores
     * @return Cadena actualizada
     */
    public static String translation(String str, String source, String dest, String mods) {
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
            return replacer.doReplacement(str);
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
