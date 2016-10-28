package perldoop.lib;

import java.io.IOException;
import perldoop.lib.file.Fread;
import perldoop.lib.file.Fwrite;

/**
 * Clase que representa un fichero en Perl
 *
 * @author César Pomar
 */
public final class PerlFile {

    public static PerlFile STDIN = new PerlFile(new Fread());
    public static PerlFile STDOUT = new PerlFile(new Fwrite(Fwrite.STDOUT));
    public static PerlFile STDERR = new PerlFile(new Fwrite(Fwrite.STDERR));

    private Fread read;
    private Fwrite write;

    /**
     * Crea un fichero con el descriptor de entrada
     *
     * @param read Decriptor de entrada
     */
    public PerlFile(Fread read) {
        this.read = read;
    }

    /**
     * Crea un fichero con el descriptor de salida
     *
     * @param write Descritor de salida
     */
    public PerlFile(Fwrite write) {
        this.write = write;
    }

    /**
     *
     * @param path Ruta del fichero
     * @param mode Finalidad de apertura del fichero
     * @return 1 si tuvo existe, 0 en otro caso
     */
    public Integer open(String path, String mode) {
        try {
            switch (mode) {
                case "<":
                    read = new Fread(path);
                    break;
                case ">":
                    write = new Fwrite(path, false);
                    break;
                case ">>":
                    write = new Fwrite(path, true);
                    break;
            }
        } catch (IOException ex) {
            return 0;
        }
        return 1;
    }

    /**
     * Cierra el fichero
     *
     * @return 1 si tuvo existe, 0 en otro caso
     */
    public Integer close() {
        try {
            if (read != null) {
                read.close();
                read = null;
            }
            if (write != null) {
                write.close();
                write = null;
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
    public int print(Object... values) {
        if (write != null) {
            return write.print(values);
        }
        return 0;
    }

    /**
     * Escribe valores en el fichero
     *
     * @param values valores
     * @return 1 si tiene exito, 0 en caso contrario
     */
    public int println(Object... values) {
        if (write != null) {
            return write.println(values);
        }
        return 0;
    }

    /**
     * lee una linea del fichero
     *
     * @return linea
     */
    public String read() {
        if (read != null) {
            return read.read();
        }
        return "";
    }

    /**
     * Lee todo el fichero
     *
     * @return array de lineas
     */
    public String[] readLines() {
        if (read != null) {
            return read.readLines();
        }
        return new String[0];
    }

    @Override
    public String toString() {
        return "GLOB(" +super.toString().substring(1)+ ')';
    }

}
