package perldoop.error;

import java.util.List;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.Opciones;
import perldoop.modelo.lexico.Token;

/**
 * Gestiona la salida de msgs del programa
 *
 * @author César Pomar
 */
public final class GestorErrores {

    private String fichero;
    private StringBuilder codigo;
    private Errores msgs;
    private Opciones opciones;
    private int errores;
    private int avisos;

    /**
     * Crear el gestor de msgs
     *
     * @param fichero Nombre del fichero del codigo fuente
     * @param opciones Opciones
     */
    public GestorErrores(String fichero, Opciones opciones) {
        this.fichero = fichero;
        this.opciones = opciones;
        msgs = new Errores();
    }

    /**
     * Establece el código fuente en analisis
     *
     * @param codigo Código fuente en analisis
     */
    public void setCodigo(StringBuilder codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene los errores
     *
     * @return Numero de errores
     */
    public int getErrores() {
        return errores;
    }

    /**
     * Obtiene los avisos
     *
     * @return Avisos
     */
    public int getAvisos() {
        return avisos;
    }

    /**
     * Obtiene el nombre del fichero
     *
     * @return Nombre del fichero
     */
    public String getFichero() {
        return fichero;
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
        if(error(tipo, (t.getLinea() + 1) + ":" + (t.getColumna()) + ": " + msgs.get(tipo) + ": " + msgs.get(codigo, args))){
            mostrarCodigo(t);
        }
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
     * Imprime un error de consola
     *
     * @param codigo Identificador del error
     * @param args Valores para el mensaje de error
     */
    public void error(String codigo, Object... args) {
        error(Errores.ERROR, msgs.get(codigo, args));
    }

    /**
     * Muestra un error al usuario
     *
     * @param tipo Tipo error
     * @param msg Mensaje
     * @return Error lanzado
     */
    private boolean error(String tipo, String msg) {
        if (Errores.AVISO.equals(tipo)) {
            if (!opciones.isOcultarAvisos()) {
                System.err.println(fichero + ":" + msg);
                return true;
            }
            avisos++;
        } else {
            errores++;
            if (opciones.getMostrarErrores() == null || opciones.getMostrarErrores() > errores) {
                System.err.println(fichero + ":" + msg);
                return true;
            }
        }
        return false;
    }

    /**
     * Muestra al usuario un fragmento del codigo con la posicion del error
     *
     * @param t Token
     */
    private void mostrarCodigo(Token t) {
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
                fin = i;
                break;
            }
        }
        //Cogemos como maximo 30 char de cada lado
        if (fin - pos > 30) {
            fin = pos + 30;
        }
        if (pos - inicio > 30) {
            inicio = pos - 30;
        }
        System.err.println("\t" + codigo.subSequence(inicio, fin));
        System.err.println("\t" + String.format("%" + (pos - inicio + 1) + "s", "^"));
    }

}
