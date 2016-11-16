package perldoop.lib.file;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * Clase para almacenar un fichero de escritura.
 *
 * @author César Pomar
 */
public final class Fwrite implements Closeable {

    public static final boolean STDOUT = false;
    public static final boolean STDERR = true;

    private OutputStream output;
    private Writer escritura;
    private BufferedWriter buffer;

    /**
     * Escribe por pantalla
     *
     * @param flag STDOUT || STDERR
     */
    public Fwrite(boolean flag) {
        if (flag == STDOUT) {
            buffer = new BufferedWriter(new OutputStreamWriter(System.out));
        } else {
            buffer = new BufferedWriter(new OutputStreamWriter(System.err));
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
        escritura = new FileWriter(path, append);
        buffer = new BufferedWriter(escritura);

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
        output = new FileOutputStream(path);
        escritura = new OutputStreamWriter(output, encode);
        buffer = new BufferedWriter(escritura);
    }

    /**
     * Cierra el fichero de escritura
     */
    @Override
    public void close() throws IOException {
        buffer.close();
        escritura.close();
        if (output != null) {
            output.close();
        }
    }

    /**
     * Escribe valores en el fichero
     *
     * @param values valores
     * @return 1 si tiene exito, 0 en caso contrario
     */
    public int print(Object... values) {
        try {
            for (Object value : values) {
                buffer.write(value.toString());
            }
        } catch (IOException ex) {
            return 0;
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
        try {
            for (Object value : values) {
                buffer.write(value.toString());
                buffer.newLine();
            }
        } catch (IOException ex) {
            return 0;
        }
        return 1;
    }

}
