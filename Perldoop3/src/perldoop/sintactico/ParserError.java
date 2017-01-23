package perldoop.sintactico;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.sintactico.ParserVal;

/**
 * Clase auxiliar para gestionar los errores sintacticos fuera del fichero de definición del analizador.
 *
 * @author César Pomar
 */
public class ParserError {

    /**
     * Metodo invocado por el analizador en caso de error, en este metodo se recolecta y prepara la información a
     * mostrar para luego invocar al sistema de errores.
     *
     * @param parser Parser
     * @param listaEsperados Lista de tokens esperados
     */
    public static void errorSintactico(Parser parser, List<Integer> listaEsperados) {
        Set<String> tokens = new LinkedHashSet<>(30);
        Set<String> otros = new LinkedHashSet<>(30);
        Set<Integer> setEsperados = new HashSet<>(listaEsperados);
        Token token = ((Terminal) parser.yylval.get()).getToken();
        //Cargamos la pila en hash para consultas rapidas
        Set<Integer> pila = new HashSet<>(10);
        for (ParserVal pv : parser.valstk) {
            if (pv != null && pv.get() instanceof Terminal) {
                Terminal t = (Terminal) pv.get();
                pila.add(t.getToken().getTipo());
            }
        }
        //Separadores de sentencias
        if (setEsperados.contains((int) ';')) {
            tokens.add(repr(';'));
        } else if (setEsperados.contains((int) ',')) {
            tokens.add(repr(','));
        } //Else o Elsif
        if (token.getTipo() == Parser.ELSE || token.getTipo() == Parser.ELSIF) {
            tokens.add("IF");
            tokens.add("UNLESS");
        }//bloques abiertos no cerrados y cerrados no abiertos
        else if (token.getValor().equals("EOF") && pila.contains((int) '{')) {
            tokens.add(repr('}'));
        } else if (token.getTipo() == '}' && !pila.contains((int) '{')) {
            tokens.add(repr('{'));
        } else if (token.getValor().equals("EOF") && pila.contains((int) '[')) {
            tokens.add(repr(']'));
        } else if (token.getTipo() == ']' && !pila.contains((int) '[')) {
            tokens.add(repr('['));
        } else if (token.getValor().equals("EOF") && pila.contains((int) '(')) {
            tokens.add(repr(')'));
        } else if (token.getTipo() == ')' && !pila.contains((int) '(')) {
            tokens.add(repr('('));
        }

        for (int i : listaEsperados) {
            switch (i) {
                //Expresiones
                case Parser.VAR:
                case Parser.FILE:
                case Parser.ENTERO:
                case Parser.DECIMAL:
                case Parser.M_REX:
                case Parser.S_REX:
                case Parser.Y_REX:
                case Parser.TEXTO:
                case Parser.EXP_SEP:
                case Parser.SEP:
                case Parser.STDIN:
                case Parser.STDOUT:
                case Parser.STDERR:
                case Parser.STDOUT_H:
                case Parser.STDERR_H:
                case Parser.ID:
                case Parser.ID_P:
                case Parser.ID_L:
                    tokens.add("EXP");
                    break;
                //Operaciones
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
                case Parser.DLOR_IGUAL:
                case Parser.CONCAT_IGUAL:
                case Parser.X_IGUAL:
                case Parser.LOR:
                case Parser.DLOR:
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
                    tokens.add("OP");
                    break;
                //otros
                default:
                    otros.add(repr(i));
            }
        }
        if(tokens.isEmpty()){
            tokens = otros;
        }
        tokens.remove("");
        parser.getGestorErrores().error(Errores.ERROR_SINTACTICO, token, new ArrayList<>(tokens));
    }

    /**
     * Representacion de los tokens
     *
     * @param tipo Tipo del token
     * @return Cadena representacion
     */
    private static String repr(int tipo) {
        if (tipo < 256) {
            return "'" + (char) tipo + "'";
        }
        switch (tipo) {
            case Parser.VAR:
                return "VAR";
            case Parser.FILE:
                return "FILE";
            case Parser.ENTERO:
                return "INTEGER";
            case Parser.DECIMAL:
                return "DECIMAL";
            case Parser.M_REX:
                return "M_REX";
            case Parser.S_REX:
                return "S_REX";
            case Parser.Y_REX:
                return "Y_REX";
            case Parser.TEXTO:
                return "TEXT";
            case Parser.EXP_SEP:
                return "EXP_SEP";
            case Parser.REX_MOD:
                return "REX_MOD";
            case Parser.SEP:
                return "SEP";
            case Parser.STDIN:
                return "STDIN";
            case Parser.STDOUT:
                return "STDOUT";
            case Parser.STDERR:
                return "STDERR";
            case Parser.STDOUT_H:
                return "STDOUT_H";
            case Parser.STDERR_H:
                return "STDERR_H";
            case Parser.MY:
                return "MY";
            case Parser.SUB:
                return "SUB";
            case Parser.OUR:
                return "OUR";
            case Parser.PACKAGE:
                return "PACKAGE";
            case Parser.WHILE:
                return "WHILE";
            case Parser.DO:
                return "DO";
            case Parser.FOR:
                return "FOR";
            case Parser.UNTIL:
                return "UNTIL";
            case Parser.USE:
                return "USE";
            case Parser.IF:
                return "IF";
            case Parser.ELSIF:
                return "ELSIF";
            case Parser.ELSE:
                return "ELSE";
            case Parser.UNLESS:
                return "UNLESS";
            case Parser.LAST:
                return "LAST";
            case Parser.NEXT:
                return "NEXT";
            case Parser.RETURN:
                return "RETURN";
            case Parser.Q:
                return "Q";
            case Parser.QQ:
                return "QQ";
            case Parser.QR:
                return "QR";
            case Parser.QW:
                return "QW";
            case Parser.QX:
                return "QX";
            case Parser.LLOR:
                return "||";
            case Parser.LLXOR:
                return "xor";
            case Parser.LLAND:
                return "&&";
            case Parser.LLNOT:
                return "not";
            case Parser.MULTI_IGUAL:
                return "*=";
            case Parser.DIV_IGUAL:
                return "/=";
            case Parser.MOD_IGUAL:
                return "%=";
            case Parser.MAS_IGUAL:
                return "+=";
            case Parser.MENOS_IGUAL:
                return "-=";
            case Parser.DESP_I_IGUAL:
                return "<<=";
            case Parser.DESP_D_IGUAL:
                return ">>=";
            case Parser.AND_IGUAL:
                return "&=";
            case Parser.OR_IGUAL:
                return "|=";
            case Parser.XOR_IGUAL:
                return "^=";
            case Parser.POW_IGUAL:
                return "**=";
            case Parser.LAND_IGUAL:
                return "&&=";
            case Parser.LOR_IGUAL:
                return "||=";
            case Parser.DLOR_IGUAL:
                return "//=";
            case Parser.CONCAT_IGUAL:
                return ".=";
            case Parser.X_IGUAL:
                return "x=";
            case Parser.ID:
                return "ID";
            case Parser.DOS_PUNTOS:
                return "..";
            case Parser.LOR:
                return "||";
            case Parser.DLOR:
                return "//";
            case Parser.LAND:
                return "&&";
            case Parser.NUM_EQ:
                return "==";
            case Parser.NUM_NE:
                return "!=";
            case Parser.STR_EQ:
                return "eq";
            case Parser.STR_NE:
                return "ne";
            case Parser.CMP:
                return "cmp";
            case Parser.CMP_NUM:
                return "<=>";
            case Parser.SMART_EQ:
                return "~~";
            case Parser.NUM_LE:
                return ">=";
            case Parser.NUM_GE:
                return "<=";
            case Parser.STR_LT:
                return "lt";
            case Parser.STR_LE:
                return "lt";
            case Parser.STR_GT:
                return "gt";
            case Parser.STR_GE:
                return "ge";
            case Parser.DESP_I:
                return "<<";
            case Parser.DESP_D:
                return ">>";
            case Parser.X:
                return "x";
            case Parser.STR_REX:
                return "=~";
            case Parser.STR_NO_REX:
                return "!~";
            case Parser.POW:
                return "**";
            case Parser.MAS_MAS:
                return "++";
            case Parser.MENOS_MENOS:
                return "--";
            case Parser.FLECHA:
                return "->";
            case Parser.ID_P:
                return "ID";
            case Parser.ID_L:
                return "ID";
            case Parser.AMBITO:
                return "::";
        }
        return "";
    }

}
