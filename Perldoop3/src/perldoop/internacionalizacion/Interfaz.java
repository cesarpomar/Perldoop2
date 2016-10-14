package perldoop.internacionalizacion;

/**
 * Clase para gestionar los mensajes de la interfaz
 *
 * @author César Pomar
 */
public class Interfaz extends Internacionalizacion {

    //Información
    public static final String APP_NOMBRE = "APP_NOMBRE";
    public static final String APP_DESCRIPCION = "APP_DESCRIPCION";
    public static final String APP_VERSION = "APP_VERSION";
    //Argumentos posicionales
    public static final String ARGS_POSICIONAL = "ARGS_POSICIONAL";
    public static final String INFILE = "INFILE";
    //Argumentos opcionales
    public static final String ARGS_OPCIONAL = "ARGS_OPCIONAL";
    public static final String AYUDA = "AYUDA";
    public static final String VERSION = "VERSION";
    public static final String OUT = "OUT";
    public static final String NO_FORMATEAR = "NO_FORMATEAR";
    public static final String COPIAR_COMENTARIOS = "COPIAR_COMENTARIOS";
    public static final String OCULTAR_AVISOS = "OCULTAR_AVISOS";
    public static final String MOSTRAR_ERRORES = "MOSTRAR_ERRORES";  
    
    //Argumentos Optimización
    public static final String ARGS_OPTIMIZACION = "ARGS_OPTIMIZACION";
    public static final String OPTIMIZAR_INSTANCIAS = "OPTIMIZAR_INSTANCIAS";
    public static final String OPTIMIZAR_DIAMANTE = "OPTIMIZAR_DIAMANTE";
    public static final String OPTIMIZAR_SENTENCIAS = "OPTIMIZAR_SENTENCIAS";
    
    //Argumentos depuración
    public static final String ARGS_DEPURACION = "ARGS_DEPURACION";
    public static final String DEPURACION_TOKENS = "DEPURACION_TOKENS";
    public static final String DEPURACION_TERMINALES = "DEPURACION_TERMINALES";
    public static final String DEPURACION_ARBOL = "DEPURACION_ARBOL";
    public static final String DEPURACION_TRADUCCION = "DEPURACION_TRADUCCION";
    public static final String DEPURACION_ESTADOS = "DEPURACION_ESTADOS";

    public Interfaz() {
        super("interfaz");
    }

}
