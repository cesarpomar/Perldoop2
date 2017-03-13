package perldoop.lib;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
     * Crea un fichero para lectura o escritura
     */
    public PerlFile() {
    }

    /**
     * Crea un fichero con el descriptor de entrada
     *
     * @param read Decriptor de entrada
     */
    private PerlFile(Fread read) {
        this.read = read;
    }

    /**
     * Crea un fichero con el descriptor de salida
     *
     * @param write Descritor de salida
     */
    private PerlFile(Fwrite write) {
        this.write = write;
    }

    /**
     * Obtiene la codificacion
     *
     * @param str Cadena que contiene la codificación
     * @return Codificación
     */
    private String getEncode(String str) {
        if (str.indexOf(':') > -1) {
            String encode = str.substring(str.indexOf(':') + 1);
            if (encode.startsWith("encoding")) {
                encode = encode.substring(9, encode.length() - 1);
            }
            return encode;
        }
        return null;
    }

    /**
     * Abre un fichero
     *
     * @param path Ruta del fichero
     * @param mode Modo de apertura del fichero
     * @return 1 si tuvo existo, 0 en otro caso
     */
    public Integer open(String path, String mode) {
        String encode = getEncode(mode);
        read = null;
        write = null;
        try {
            if (mode.startsWith("<")) {
                if (encode == null) {
                    read = new Fread(path);
                } else {
                    read = new Fread(path, encode);
                }
            } else if (mode.startsWith(">")) {
                boolean append = mode.startsWith(">>");
                if (encode == null) {
                    write = new Fwrite(path, append);
                } else {
                    write = new Fwrite(path, append, encode);
                }
            }
        } catch (IOException ex) {
            return 0;
        }
        return 1;
    }

    /**
     * Cambia la codificación del fichero
     *
     * @param encode Codificación
     * @return 1 si tuvo existe, 0 en otro caso
     */
    public Integer setEnconde(String encode) {
        String encode2 = getEncode(encode);
        if (encode2 == null) {
            encode2 = encode;
        }
        try {
            if (read != null) {
                read.setEnconde(encode2);
            }
            if (write != null) {
                write.setEnconde(encode2);
            }
        } catch (UnsupportedEncodingException ex) {
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
     * Escribe valores en el fichero
     *
     * @param format Formato
     * @param values valores
     * @return 1 si tiene exito, 0 en caso contrario
     */
    public int printf(String format, Object... values) {
        if (write != null) {
            return write.printf(format, values);
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
        return "GLOB(" + super.toString() + ')';
    }

}
