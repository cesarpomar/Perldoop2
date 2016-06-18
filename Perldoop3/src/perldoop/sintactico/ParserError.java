package perldoop.sintactico;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.sintactico.ParserVal;

/**
 * Clase auxiliar para gestionar los errores sintacticos fuera del fichero de
 * definición del analizador.
 *
 * @author César Pomar
 */
public class ParserError {

    /**
     * Metodo invocado por el analizador en caso de error, en este metodo se
     * recolecta y prepara la información a mostrar para luego invocar al
     * sistema de errores.
     *
     * @param parser Parser
     * @param tiposEsperados Lista de tokens esperados
     */
    public static void errorSintactico(Parser parser, List<Integer> tiposEsperados) {
        List<Integer> pila = new ArrayList<>(10);
        for (ParserVal pv : parser.valstk) {
            if (pv != null && pv.get() instanceof Terminal) {
                Terminal t = (Terminal) pv.get();
                pila.add(t.getToken().getTipo());
            }
        }
        Set<String> tokens = new HashSet<>(30);

        for (int t : tiposEsperados) {
            switch (t) {
                case '}':
                    if (pila.contains((int) '{')) {
                        tokens.add("'" + repr(t) + "'");
                    }
                case ')':
                    if (pila.contains((int) '(')) {
                        tokens.add("'" + repr(t) + "'");
                    }
                    break;
                case ']':
                    if (pila.contains((int) '[')) {
                        tokens.add("'" + repr(t) + "'");
                    }
                default:
                    tokens.add("'" + repr(t) + "'");
            }
        }
        List<String> tokensEsperados = new ArrayList<>(tokens.size());
        for (String token : tokens) {
            if (!token.isEmpty()) {
                tokensEsperados.add(token);
            }
        }
        parser.getGestorErrores().error(Errores.ERROR_SINTACTICO, ((Terminal) parser.yylval.get()).getToken(), tokensEsperados);

    }

    private static String repr(int tipo) {
        switch (tipo) {
            case Parser.ID:
                return "ID";
            case Parser.ENTERO:
            case Parser.DECIMAL:
            case Parser.CADENA_SIMPLE:
            case Parser.CADENA_DOBLE:
            case Parser.CADENA_COMANDO:
            case Parser.M_REGEX:
            case Parser.S_REGEX:
            case Parser.Y_REGEX:
                return "EXP";
            case Parser.STDIN:
                return "<STDIN>";
            case Parser.STDOUT:
                return "<STDOUT>";
            case Parser.STDERR:
                return "<STDERR>";
            case Parser.MY:
                return "my";
            case Parser.SUB:
                return "sub";
            case Parser.OUR:
                return "our";
            case Parser.PACKAGE:
                return "";
            case Parser.WHILE:
                return "while";
            case Parser.DO:
                return "do";
            case Parser.FOR:
                return "for";
            case Parser.UNTIL:
                return "until";
            case Parser.IF:
                return "if";
            case Parser.ELSIF:
                return "elsif";
            case Parser.ELSE:
                return "else";
            case Parser.UNLESS:
                return "unless";
            case Parser.LAST:
            case Parser.NEXT:
            case Parser.RETURN:
                return "";
            case '*':
            case '/':
            case '.':
            case '|':
            case '&':
            case '~':
            case '^':
            case '<':
            case '>':
            case '=':
            case Parser.LLOR:
            case Parser.LLXOR:
            case Parser.LLAND:
            case Parser.LLNOT:
            case Parser.MULTI_IGUAL:
            case Parser.DIV_IGUAL:
            case Parser.MOD_IGUAL:
            case Parser.SUMA_IGUAL:
            case Parser.MAS_IGUAL:
            case Parser.MENOS_IGUAL:
            case Parser.DESP_I_IGUAL:
            case Parser.DESP_D_IGUAL:
            case Parser.AND_IGUAL:
            case Parser.OR_IGUAL:
            case Parser.XOR_IGUAL:
            case Parser.POW_IGUAL:
            case Parser.LAND_IGUAL:
            case Parser.LOR_IGUAL:
            case Parser.CONCAT_IGUAL:
            case Parser.X_IGUAL:
            case Parser.DOS_PUNTOS:
            case Parser.LOR:
            case Parser.SUELO:
            case Parser.LAND:
            case Parser.NUM_EQ:
            case Parser.NUM_NE:
            case Parser.STR_EQ:
            case Parser.STR_NE:
            case Parser.CMP:
            case Parser.CMP_NUM:
            case Parser.SMART_EQ:
            case Parser.NUM_LE:
            case Parser.NUM_GE:
            case Parser.STR_LT:
            case Parser.STR_LE:
            case Parser.STR_GT:
            case Parser.STR_GE:
            case Parser.DESP_I:
            case Parser.DESP_D:
            case Parser.X:
            case Parser.STR_REX:
            case Parser.STR_NO_REX:
            case Parser.POW:
                return "OP";
            case Parser.MAS_MAS:
                return "++";
            case Parser.MENOS_MENOS:
                return "--";
            case Parser.FLECHA:
                return "->";
            case Parser.AMBITO:
                return "::";
        }
        if (tipo < 256) {
            return "" + (char) tipo;
        }
        return "";
    }

}
