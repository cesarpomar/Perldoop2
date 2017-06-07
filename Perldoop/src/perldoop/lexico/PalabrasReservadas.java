package perldoop.lexico;

import perldoop.preprocesador.Preprocesador;
import perldoop.sintactico.Parser;

/**
 * Clase para buscar simbolos reservados y asociarles su tipo.
 *
 * @author César Pomar
 */
public final class PalabrasReservadas {

    /**
     * Busca un identificador y retorna su tipo.
     *
     * @param id Id
     * @param defecto Valor por defecto si no existe
     * @return Tipo asociado al id
     */
    public static int buscarId(String id, int defecto) {
        if (id != null) {
            switch (id) {
                case "my":
                    return Parser.MY;
                case "our":
                    return Parser.OUR;
                case "sub":
                    return Parser.SUB;
                case "package":
                    return Parser.PACKAGE;
                case "while":
                    return Parser.WHILE;
                case "do":
                    return Parser.DO;
                case "for":
                case "foreach":
                    return Parser.FOR;
                case "until":
                    return Parser.UNTIL;
                case "if":
                    return Parser.IF;
                case "elsif":
                    return Parser.ELSIF;
                case "else":
                    return Parser.ELSE;
                case "unless":
                    return Parser.UNLESS;
                case "last":
                    return Parser.LAST;
                case "next":
                    return Parser.NEXT;
                case "return":
                    return Parser.RETURN;
                case "use":
                case "require":
                    return Parser.USE;
                case "and":
                    return Parser.LLAND;
                case "not":
                    return Parser.LLNOT;
                case "or":
                    return Parser.LLOR;
                case "xor":
                    return Parser.LLXOR;
                case "eq":
                    return Parser.STR_EQ;
                case "ge":
                    return Parser.STR_GE;
                case "gt":
                    return Parser.STR_GT;
                case "le":
                    return Parser.STR_LE;
                case "lt":
                    return Parser.STR_LT;
                case "ne":
                    return Parser.STR_NE;
                case "cmp":
                    return Parser.STR_CMP;
                case "STDIN":
                    return Parser.STDIN;
                case "STDOUT":
                    return Parser.STDOUT;
                case "STDERR":
                    return Parser.STDERR;
            }
        }
        return defecto;
    }

    /**
     * Comrpeuba si el simbolo pertenece a una variable especial de Perl
     *
     * @param var Variable simbolica
     * @return Variable es especial
     */
    public static boolean isPerlSpecialVar(char var) {
        switch (var) {
            case '&':
            case '`':
            case '\'':
            case '+':
            case '-':
            case '^':
            case '.':
            case '|':
            case ',':
            case '\\':
            case '/':
            case '"':
            case '%':
            case '=':
            case '~':
            case ';':
            case ':':
            case '?':
            case '!':
            case '@':
            case '$':
            case '<':
            case '>':
            case '(':
            case ')':
            case ']':
            case '[':
            case '{':
                return true;
            default:
                return false;
        }
    }

    /**
     * Comprueba si existe una etiqueta y retorno su tipo
     *
     * @param e Etiqueta
     * @return Tipo de la etiqueta o null si no existe
     */
    public static Integer buscarEtiqueta(String e) {
        if (e != null) {
            switch (e) {
                case "<boolean>":
                case "<integer>":
                case "<long>":
                case "<float>":
                case "<double>":
                case "<string>":
                case "<file>":
                case "<box>":
                case "<number>":
                    return Preprocesador.PD_TIPO;
                case "<array>":
                case "<list>":
                case "<hash>":
                case "<map>":
                    return Preprocesador.PD_COL;
                case "<ref>":
                    return Preprocesador.PD_REF;
                case "<smart>":
                    return Preprocesador.PD_SMART;
                case "<main>":
                case "<function>":
                case "<combine>":
                case "<reduction>":
                    return Preprocesador.PD_BLOQUE;
                case "<mapper>":
                    return Preprocesador.PD_MAPPER;
                case "<reduccer>":
                    return Preprocesador.PD_REDUCCER;
                case "<storm>":
                    return Preprocesador.PD_STORM;
            }
        }
        return null;
    }

}
