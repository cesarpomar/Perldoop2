package perldoop.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Clase para la lectura de ficheros fuente
 * @author César Pomar
 */
public class CodigoReader extends Reader{
    
    private File fichero;
    private FileReader lectura;
    private BufferedReader buffer;
    private StringBuilder codigo;
    
    /**
     * Abre un fichero de codigo fuente
     * @param ruta Ruta del fichero
     * @throws FileNotFoundException Fichero no existe
     */
    public CodigoReader(String ruta) throws FileNotFoundException{
        fichero = new File(ruta);
        lectura = new FileReader(fichero);
        buffer = new BufferedReader(lectura);
        codigo = new StringBuilder((int)fichero.length());
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int leido = buffer.read(cbuf, off, len);
        codigo.append(cbuf);
        return leido;
    }

    @Override
    public void close() throws IOException {
        lectura.close();
    }

    /**
     * Obtiene el código fuente
     * @return Código fuente
     */
    public StringBuilder getCodigo() {
        return codigo;
    }

}
