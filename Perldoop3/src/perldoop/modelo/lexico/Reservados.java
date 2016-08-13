package perldoop.modelo.lexico;

import perldoop.sintactico.Parser;

/**
 * Clase para buscar simbolos reservados y asociarles su tipo.
 *
 * @author César Pomar
 */
public class Reservados {

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
            }
        }
        return defecto;
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
                    return (int) Parser.PD_TIPO;
                case "<array>":
                case "<list>":
                case "<hash>":
                case "<map>":
                    return (int) Parser.PD_COL;
            }
        }
        return null;
    }

}
