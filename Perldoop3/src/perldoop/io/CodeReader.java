package perldoop.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * Clase para la lectura de ficheros fuente
 *
 * @author César Pomar
 */
public class CodeReader extends Reader {

    private File ruta;
    private InputStream input;
    private Reader lectura;
    private BufferedReader buffer;
    private StringBuilder codigo;

    /**
     * Abre un fichero de codigo fuente
     *
     * @param ruta Ruta del fichero
     * @param encode Codificacion
     * @throws FileNotFoundException Fichero no existe
     * @throws java.io.UnsupportedEncodingException Codificación erronea
     */
    public CodeReader(File ruta, String encode) throws FileNotFoundException, UnsupportedEncodingException {
        this.ruta = ruta;
        if (encode == null) {
            lectura = new FileReader(ruta);
        } else {
            input = new FileInputStream(ruta);
            lectura = new InputStreamReader(input, encode);
        }
        buffer = new BufferedReader(lectura);
        codigo = new StringBuilder((int) ruta.length());
    }

    /**
     * Abre un fichero de codigo fuente
     *
     * @param ruta Ruta del fichero
     * @throws FileNotFoundException Fichero no existe
     */
    public CodeReader(File ruta) throws FileNotFoundException {
        this.ruta = ruta;
        lectura = new FileReader(ruta);
        buffer = new BufferedReader(lectura);
        codigo = new StringBuilder((int) ruta.length());
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int leido = buffer.read(cbuf, off, len);
        if (leido > 0) {
            codigo.append(cbuf, off, leido);
        }
        return leido;
    }

    @Override
    public void close() throws IOException {
        if (input != null) {
            input.close();
        } else {
            lectura.close();
        }
    }

    /**
     * Obtiene el código fuente
     *
     * @return Código fuente
     */
    public StringBuilder getCodigo() {
        return codigo;
    }

    /**
     * Obtiene el fichero
     *
     * @return Fichero
     */
    public File getRuta() {
        return ruta;
    }

}
