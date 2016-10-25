package perldoop.lib.file;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para almacenar un fichero de lectura.
 *
 * @author César Pomar
 */
public final class Fread implements Closeable {

    private BufferedReader buffer;
    private FileReader file;
    private boolean cerrado;

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
     * @throws java.io.FileNotFoundException Si el fichero no existe
     */
    public Fread(String path) throws FileNotFoundException {
        file = new FileReader(path);
        buffer = new BufferedReader(file);
    }

    /**
     * Cierra el fichero de escritura
     */
    @Override
    public void close() throws IOException {
        cerrado=true;
        buffer.close();
    }

    /**
     * lee una linea del fichero
     *
     * @return linea
     */
    public String read() {
        if(cerrado){
            return "";
        }
        try {
            String line = buffer.readLine();
            if (line != null) {
                return line + "\n";
            }
        } catch (IOException ex) {
            Logger.getLogger(Fread.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    /**
     * Lee todo el fichero
     *
     * @return array de lineas
     */
    public String[] readLines() {
        if(cerrado){
            return new String[0];
        }
        return buffer.lines().map(line -> line + "\n").toArray(String[]::new);
    }

}
