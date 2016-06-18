package perldoop.internacionalizacion;

/**
 * Clase para gestionar los mensajes de errores de la apliación
 *
 * @author César Pomar
 */
public class Errores extends Internacionalizacion {

    //Tipos
    public static final String ERROR = "ERROR";
    public static final String AVISO = "AVISO";
    //Lexicos
    public static final String CARACTER_INVALIDO = "CARACTER_INVALIDO";
    public static final String FALLOS_LEXICOS = "FALLOS_LEXICOS";
    public static final String ETIQUETA_IGNORADA = "ETIQUETA_IGNORADA";
    //Preprocesador
    public static final String ETIQUETA_NO_USADA = "ETIQUETA_NO_USADA";
    public static final String FALLOS_SINTACTICOS = "FALLOS_SINTACTICOS";
    //Sintacticos
    public static final String ERROR_SINTACTICO = "ERROR_SINTACTICO";
    public static final String FALLOS_SEMANTICO = "FALLOS_SEMANTICO";
    //Semanticos

    public Errores() {
        super("errores");
    }

}
