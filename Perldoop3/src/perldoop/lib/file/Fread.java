package perldoop.lib.file;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Clase para almacenar un fichero de lectura.
 *
 * @author César Pomar
 */
public final class Fread implements Closeable {

    private InputStream stream;
    private BufferedReader in;

    /**
     * Usa la entrada estandar para la lectura
     */
    public Fread() {
        in = new BufferedReader(new InputStreamReader(stream = System.in));
    }

    /**
     * Abre un fichero para lectura
     *
     * @param path Ruta del fichero
     * @throws FileNotFoundException Si el fichero no existe
     */
    public Fread(String path) throws FileNotFoundException {
        in = new BufferedReader(new InputStreamReader(stream = new FileInputStream(path)));
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
        in = new BufferedReader(new InputStreamReader(stream = new FileInputStream(path), encode));
    }

    /**
     * Cambia la codificación del fichero
     *
     * @param encode Codificación
     * @throws UnsupportedEncodingException Codificación no soportada
     */
    public void setEnconde(String encode) throws UnsupportedEncodingException {
        in = new BufferedReader(new InputStreamReader(stream, encode));
    }

    /**
     * Cierra el fichero de escritura
     */
    @Override
    public void close() throws IOException {
        in.close();
    }

    /**
     * Lee una linea del fichero
     *
     * @return linea
     */
    public String read() {
        try {
            String line = in.readLine();
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
        return in.lines().map(line -> line + "\n").toArray(String[]::new);
    }

}
