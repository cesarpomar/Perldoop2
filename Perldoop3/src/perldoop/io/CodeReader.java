package perldoop.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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
     * @param encode Codificación
     * @throws FileNotFoundException Fichero no existe
     */
    private CodeReader(File ruta, Charset encode) throws FileNotFoundException {
        input = new FileInputStream(ruta);
        lectura = new InputStreamReader(input, encode);
        buffer = new BufferedReader(lectura);
        codigo = new StringBuilder((int) ruta.length() + 10);
    }

    /**
     * Abre un fichero de codigo fuente
     *
     * @param ruta Ruta del fichero
     * @param encode Codificacion
     * @throws FileNotFoundException Fichero no existe
     * @throws java.io.UnsupportedEncodingException Codificación erronea
     */
    public CodeReader(File ruta, String encode) throws FileNotFoundException, UnsupportedEncodingException {
        this(ruta, encode != null ? Charset.forName(encode) : StandardCharsets.UTF_8);
    }

    /**
     * Abre un fichero de codigo fuente
     *
     * @param ruta Ruta del fichero
     * @throws FileNotFoundException Fichero no existe
     */
    public CodeReader(File ruta) throws FileNotFoundException {
        this(ruta, StandardCharsets.UTF_8);
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
