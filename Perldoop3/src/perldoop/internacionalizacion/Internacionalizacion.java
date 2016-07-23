package perldoop.internacionalizacion;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Clase para gestionar los mensajes del fichero de internacionalización
 *
 * @author César Pomar
 */
public class Internacionalizacion {

    private ResourceBundle texto;

    /**
     * Contructor de la clase
     *
     * @param fichero Fichero properties
     */
    public Internacionalizacion(String fichero) {
        texto = ResourceBundle.getBundle("perldoop.internacionalizacion." + fichero);
    }

    /**
     * Obtiene un mensaje
     *
     * @param codigo Clave del mensaje
     * @param args Valores de remplazo en el mensaje
     * @return Mensaje
     */
    public final String get(String codigo, Object... args) {
        MessageFormat formato = new MessageFormat(texto.getString(codigo));
        return formato.format(args);
    }

    /**
     * Obtiene un mensaje
     *
     * @param codigo Clave del mensaje
     * @return Mensaje
     */
    public final String get(String codigo) {
        return texto.getString(codigo);
    }
    
}
