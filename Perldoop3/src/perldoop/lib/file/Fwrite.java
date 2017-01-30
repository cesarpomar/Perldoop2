package perldoop.lib.file;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * Clase para almacenar un fichero de escritura.
 *
 * @author César Pomar
 */
public final class Fwrite implements Closeable {

    public static final boolean STDOUT = true;
    public static final boolean STDERR = false;

    private OutputStream stream;//Stream que representa el dispositivo de escritura
    private PrintStream out;//Encargado de realizar las escrituras

    /**
     * Escribe por pantalla
     *
     * @param flag STDOUT || STDERR
     */
    public Fwrite(boolean flag) {
        if (flag) {
            stream = out = System.out;
        } else {
            stream = out = System.err;
        }
    }

    /**
     * Abre el fichero para la escritura
     *
     * @param path Ruta del fichero
     * @param append Continuar fichero existente
     * @throws IOException Error al abrir el fichero
     */
    public Fwrite(String path, boolean append) throws IOException {
        out = new PrintStream(stream = new BufferedOutputStream(new FileOutputStream(path, append)));
    }

    /**
     * Abre el fichero para la escritura
     *
     * @param path Ruta del fichero
     * @param append Continuar fichero existente
     * @param encode Codificación
     * @throws UnsupportedEncodingException Codificación no soportada
     * @throws IOException Error al abrir el fichero
     */
    public Fwrite(String path, boolean append, String encode) throws UnsupportedEncodingException, IOException {
        out = new PrintStream(stream = new BufferedOutputStream(new FileOutputStream(path, append)), false, encode);
    }

    /**
     * Cambia la codificación del fichero
     *
     * @param encode Codificación
     * @throws UnsupportedEncodingException Codificación no soportada
     */
    public void setEnconde(String encode) throws UnsupportedEncodingException {
        out = new PrintStream(stream, false, encode);
    }

    /**
     * Cierra el fichero de escritura
     */
    @Override
    public void close() throws IOException {
        out.close();
    }

    /**
     * Escribe valores en el fichero
     *
     * @param values valores
     * @return 1 si tiene exito, 0 en caso contrario
     */
    public int print(Object... values) {
        for (Object value : values) {
            out.print(value);
        }
        return 1;
    }

    /**
     * Escribe valores en el fichero
     *
     * @param values valores
     * @return 1 si tiene exito, 0 en caso contrario
     */
    public int println(Object... values) {
        for (Object value : values) {
            out.print(value);
        }
        out.println();
        return 1;
    }

    /**
     * Escribe valores en el fichero
     *
     * @param format Formato
     * @param values valores
     * @return 1 si tiene exito, 0 en caso contrario
     */
    public int printf(String format, Object... values) {
        out.printf(format, values);
        return 1;
    }

}
