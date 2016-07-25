package perldoop.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Clase para la lectura de ficheros fuente
 *
 * @author César Pomar
 */
public class CodeReader extends Reader {

    private File ruta;
    private FileReader lectura;
    private BufferedReader buffer;
    private StringBuilder codigo;

    /**
     * Abre un fichero de codigo fuente
     *
     * @param ruta Ruta del fichero
     * @throws FileNotFoundException Fichero no existe
     */
    public CodeReader(String ruta) throws FileNotFoundException {
        this(new File(ruta));
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
        lectura.close();
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
