package perldoop.depurador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.lexico.Token;
import perldoop.util.ParserEtiquetas;

/**
 * Clase para depurar el analisis
 *
 * @author César Pomar
 */
public class Depurador {

    /**
     * Imprime todos los tokens
     *
     * @param tokens Lista de tokens
     */
    public static void tokens(List<Token> tokens) {
        StringBuilder salida;
        for (Token t : tokens) {
            salida = new StringBuilder(60);
            salida.append(t.getPosicion()).append(':');
            salida.append(t.getLinea()).append(':');
            salida.append(t.getColumna()).append(':');
            salida.append(t.getTipo()).append(':');
            salida.append(t.getValor());
            System.out.println(salida);
        }
    }

    /**
     * Imprime todos los terminales
     *
     * @param terminales Terminales
     */
    public static void terminales(List<Terminal> terminales) {
        for (Terminal t : terminales) {
            imprimir(t, 0, false);
        }
    }

    /**
     * Imprime el arbol sintactico
     *
     * @param raiz Raiz del arbol sintactico
     * @param traduccion Mostrar elementos referentes a la traducción
     */
    public static void arbol(Simbolo raiz, boolean traduccion) {
        int pila = 0;
        List<List<Simbolo>> simbolos = new ArrayList<>(100);
        List<Simbolo> nivel = new ArrayList<>(10);
        Simbolo s = raiz;
        nivel.add(s);
        simbolos.add(nivel);
        while (!simbolos.isEmpty()) {
            nivel = simbolos.get(pila);
            if (nivel.isEmpty()) {
                simbolos.remove(pila);
                pila--;
                continue;
            }
            s = nivel.remove(0);
            imprimir(s, pila, traduccion);
            nivel = new ArrayList<>(Arrays.asList(s.getHijos()));
            simbolos.add(nivel);
            pila++;
        }
    }

    /**
     * Imprime un Simbolo identando segun el nivel de anidamiento
     *
     * @param s Simbolo a imprimir
     * @param nivel nivel de identación
     * @param traduccion Mostrar elementos referentes a la traducción
     */
    private static void imprimir(Simbolo s, int nivel, boolean traduccion) {
        StringBuilder salida = new StringBuilder(60);
        for (int i = 0; i < nivel; i++) {
            if (i % 5 != 0) {
                salida.append("+");
            } else {
                salida.append("|");
            }
        }
        salida.append(s.getClass().getSimpleName());
        if (s instanceof Terminal) {
            Token t = ((Terminal) s).getToken();
            salida.append("__Token{");
            salida.append(t.getTipo()).append(',');
            salida.append("'").append(t.getValor()).append("'");
            salida.append("} ");
            if (((Terminal) s).getEtiquetas() != null) {
                salida.append(((Terminal) s).getEtiquetas());
            }
            if (((Terminal) s).getTokensComentario() != null) {
                salida.append("Comentario");
                salida.append(((Terminal) s).getTokensComentario());
            }
        } else if (traduccion) {
            if (s.getTipo() != null) {
                salida.append("__Tipo");
                salida.append(ParserEtiquetas.parseTipo(s.getTipo()));
            }
            if (s.getCodigoGenerado() != null) {
                salida.append("__Codigo{");
                salida.append(s.getCodigoGenerado());
                salida.append("}");
            }
        }
        System.out.println(salida);
    }

}
