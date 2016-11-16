package perldoop.lib.file;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * Clase para almacenar un fichero de lectura.
 *
 * @author César Pomar
 */
public final class Fread implements Closeable {

    private InputStream input;
    private Reader lectura;
    private BufferedReader buffer;

    /**
     * Usa la entrada estandar para la lectura
     */
    public Fread() {
        buffer = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Abre un fichero para lectura
     *
     * @param path Ruta del fichero
     * @throws FileNotFoundException Si el fichero no existe
     */
    public Fread(String path) throws FileNotFoundException {
        lectura = new FileReader(path);
        buffer = new BufferedReader(lectura);
    }

    /**
     * Abre un fichero para lectura, especificando su codificación
     *
     * @param path Ruta del fichero
     * @param encode Codificación
     * @throws FileNotFoundException Fichero no existe
     * @throws UnsupportedEncodingException Codificación no soportada
     */
    public Fread(String path, String encode) throws FileNotFoundException, UnsupportedEncodingException {
        input = new FileInputStream(path);
        lectura = new InputStreamReader(input, encode);
        buffer = new BufferedReader(lectura);
    }

    /**
     * Cierra el fichero de escritura
     */
    @Override
    public void close() throws IOException {
        buffer.close();
        lectura.close();
        if (input != null) {
            input.close();
        }
    }

    /**
     * Lee una linea del fichero
     *
     * @return linea
     */
    public String read() {
        try {
            String line = buffer.readLine();
            if (line != null) {
                return line + "\n";
            } else {
                return "";
            }
        } catch (IOException ex) {
            return "";
        }
    }

    /**
     * Lee todo el fichero
     *
     * @return array de lineas
     */
    public String[] readLines() {
        return buffer.lines().map(line -> line + "\n").toArray(String[]::new);
    }

}
