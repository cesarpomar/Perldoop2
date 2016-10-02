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
    //Consola
    public static final String FICHERO_NO_EXISTE = "FICHERO_NO_EXISTE";
    public static final String ERROR_LECTURA = "ERROR_LECTURA";
    public static final String ERROR_ESCRITURA = "ERROR_ESCRITURA";
    //Lexicos
    public static final String CARACTER_INVALIDO = "CARACTER_INVALIDO";
    public static final String FALLOS_LEXICOS = "FALLOS_LEXICOS";
    public static final String ETIQUETA_IGNORADA = "ETIQUETA_IGNORADA";
    //Preprocesador
    public static final String FALLOS_PREPROCESADOR = "FALLOS_PREPROCESADOR";
    public static final String ETIQUETAS_COLECCION_INCOMPLETAS = "ETIQUETAS_COLECCION_INCOMPLETAS";
    public static final String DEMASIADAS_ETIQUETAS_SIZE = "DEMASIADAS_ETIQUETAS_SIZE";
    public static final String TIPO_EN_INICIALIZACION = "TIPO_EN_INICIALIZACION";
    public static final String PREDECLARACION_DINAMICA = "PREDECLARACION_DINAMICA";
    public static final String REF_TIPO_BASICO = "REF_TIPO_BASICO";
    public static final String REF_ANIDADA = "REF_ANIDADA";
    public static final String REF_SIZE = "REF_SIZE";
    public static final String REF_INCOMPLETA = "REF_INCOMPLETA";
    //Sintacticos
    public static final String FALLOS_SINTACTICOS = "FALLOS_SINTACTICOS";
    public static final String ERROR_SINTACTICO = "ERROR_SINTACTICO";
    //Semanticos
    public static final String FALLOS_SEMANTICO = "FALLOS_SEMANTICO";
    public static final String MODIFICAR_CONSTANTE = "MODIFICAR_CONSTANTE";
    public static final String MODIFICAR_DEFERENCIACION = "MODIFICAR_DEFERENCIACION";
    public static final String OUR_SIN_PAQUETE = "OUR_SIN_PAQUETE";
    public static final String DECLACION_CONSTANTE = "DECLACION_CONSTANTE";
    public static final String TIPO_FOREACH = "TIPO_FOREACH";
    public static final String ACCESO_DECLARACION = "ACCESO_DECLARACION";
    public static final String VARIABLE_SIN_TIPO = "VARIABLE_SIN_TIPO";
    public static final String VARIABLE_NO_EXISTE = "VARIABLE_NO_EXISTE";
    public static final String PAQUETE_NO_EXISTE = "PAQUETE_NO_EXISTE";
    public static final String ETIQUETA_SIN_USO = "ETIQUETA_SIN_USO";
    public static final String TIPO_INCORRECTO = "TIPO_INCORRECTO";
    public static final String VARIABLE_ENMASCARADA = "VARIABLE_ENMASCARADA";
    public static final String ERROR_CASTING = "ERROR_CASTING";
    public static final String ACCESO_NO_COLECCION = "ACCESO_NO_COLECCION";
    public static final String ACCESO_ERRONEO = "ACCESO_ERRONEO";
    public static final String ACCESO_VACIO = "ACCESO_VACIO";
    public static final String ACCESO_NO_REF = "ACCESO_NO_REF";
    public static final String ACCESO_REF_MULTIPLE = "ACCESO_REF_MULTIPLE";
    public static final String ACCESO_REF_ESCALAR = "ACCESO_REF_ESCALAR";
    public static final String VARIABLE_ERROR_SIGIL = "VARIABLE_ERROR_SIGIL";
    public static final String REFERENCIA_NO_COLECCION ="REFERENCIA_NO_COLECCION";

    public Errores() {
        super("errores");
    }

}
