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
     * @param codigo dentificador del error
     * @param t Token del elemento a marcar en el error
     * @param args Valores para el mensaje de error
     */
    public void error(String codigo, Token t, Object... args) {
        error(Errores.ERROR, codigo, t, args);
    }

    /**
     * Imprime un error con tipo variable
     * @param tipo Tipo del Error
     * @param codigo Identificador del error
     * @param t Token del elemento a marcar en el error
     * @param args Valores para el mensaje de error
     */
    public void error(String tipo, String codigo, Token t, Object... args) {
        System.err.println(fichero+":"+(t.getLinea()+1)+":"+(t.getColumna()+1)+": "+errores.get(tipo)+": "+errores.get(codigo, args));
    }

    /**
     * Imprime un error sintactico
     * @param codigo Identificador del error
     * @param t Token que contiene el error
     * @param tokensEsperados Tokens que huviesen sido correctos
     */
    public void error(String codigo, Token t, List<String> tokensEsperados) {
        error(codigo, t, t.getValor(), tokensEsperados.toString());
    }
}
