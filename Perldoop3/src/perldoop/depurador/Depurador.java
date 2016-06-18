package perldoop.depurador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.lexico.Token;

/**
 * Clase para depurar el analisis del traductor
 * @author César Pomar
 */
public class Depurador {

    public static void tokens(List<Token> tokens) {

    }

    public static void simbolos(Simbolo raiz) {
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
            imprimir(s, pila);
            nivel = new ArrayList<>(Arrays.asList(s.getHijos()));
            simbolos.add(nivel);
            pila++;
        }
    }

    private static void imprimir(Simbolo s, int nivel) {
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
            salida.append('(');
            salida.append(t.getLinea()).append(", ");
            salida.append(t.getColumna()).append(", ");
            salida.append('\'').append(t.getValor()).append('\'').append(", ");
            salida.append(t.getTipo());
            salida.append(')');
        }
        System.out.println(salida);
    }

}
