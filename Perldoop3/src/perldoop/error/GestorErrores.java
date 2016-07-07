package perldoop.error;

import java.util.List;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.lexico.Token;

/**
 * Gestiona la salida de errores del programa
 *
 * @author César Pomar
 */
public class GestorErrores {

    private String fichero;
    private StringBuilder codigo;
    private Errores errores;

    /**
     * Crear el gestor de errores
     *
     * @param fichero Nombre del fichero del codigo fuente
     * @param codigo Código fuente en analisis
     */
    public GestorErrores(String fichero, StringBuilder codigo) {
        this.fichero = fichero;
        this.codigo = codigo;
        errores = new Errores();
    }

    /**
     * Imprimer un error
     *
     * @param codigo dentificador del error
     * @param t Token del elemento a marcar en el error
     * @param args Valores para el mensaje de error
     */
    public void error(String codigo, Token t, Object... args) {
        error(Errores.ERROR, codigo, t, args);
    }

    /**
     * Imprime un error con tipo variable
     *
     * @param tipo Tipo del Error
     * @param codigo Identificador del error
     * @param t Token del elemento a marcar en el error
     * @param args Valores para el mensaje de error
     */
    public void error(String tipo, String codigo, Token t, Object... args) {
        System.err.println(fichero + ":" + (t.getLinea() + 1) + ":" + (t.getColumna()) + ": " + errores.get(tipo) + ": " + errores.get(codigo, args));
        mostrarCodigo(t);
    }

    /**
     * Imprime un error sintactico
     *
     * @param codigo Identificador del error
     * @param t Token que contiene el error
     * @param tokensEsperados Tokens que huviesen sido correctos
     */
    public void error(String codigo, Token t, List<String> tokensEsperados) {
        error(codigo, t, t.getValor(), tokensEsperados.toString());
    }

    /**
     * Muestra al usuario un fragmento del codigo con la posicion del error
     *
     * @param t Token
     */
    public void mostrarCodigo(Token t) {
        int inicio = 0;
        int pos = t.getPosicion();
        int fin = codigo.length();
        //Buscar inicio linea
        for (int i = pos - 1; i > -1; i--) {
            char c = codigo.charAt(i);
            if (c == '\n' || c == '\t') {
                inicio = i + 1;
                break;
            }
        }
        //Buscar fin linea
        for (int i = pos + 1; i < codigo.length(); i++) {
            char c = codigo.charAt(i);
            if (c == '\n') {
                fin = i - 1;
                break;
            }
        }
        //Cogemos como maximo 30 char de cada lado
        if (fin - pos > 30) {
            fin = pos + 30;
        }
        if (pos - inicio > 30) {
            inicio = pos - 30;
            fin = fin - 30;
            pos = 30;
        }
        System.err.println("\t"+codigo.subSequence(inicio, fin));
        System.err.println("\t"+String.format("%" + (pos - inicio) + "s", "^"));
    }

}
