package perldoop.internacionalizacion;

/**
 * Clase para gestionar los mensajes de errores de la aplicación
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
    public static final String ERROR_CODIFICACION = "ERROR_CODIFICACION";
    public static final String PAQUETE_INVALIDO = "PAQUETE_INVALIDO";
    public static final String ERROR_LIBRERIA = "ERROR_LIBRERIA";
    public static final String ERROR_FORMATEO = "ERROR_FORMATEO";

    //Lexicos
    public static final String CARACTER_INVALIDO = "CARACTER_INVALIDO";
    public static final String FALLOS_LEXICOS = "FALLOS_LEXICOS";
    public static final String ETIQUETA_IGNORADA = "ETIQUETA_IGNORADA";
    public static final String VARIABLE_IGNORADA = "VARIABLE_IGNORADA";
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
    public static final String MAPPER_INCOMPLETO = "MAPPER_INCOMPLETO";
    public static final String REDUCCER_INCOMPLETO = "REDUCCER_INCOMPLETO";
    public static final String REDUCCER_VARS = "REDUCCER_VARS";
    //Sintacticos
    public static final String FALLOS_SINTACTICOS = "FALLOS_SINTACTICOS";
    public static final String ERROR_SINTACTICO = "ERROR_SINTACTICO";
    //Semanticos
    public static final String FALLOS_SEMANTICO = "FALLOS_SEMANTICO";
    public static final String MODIFICAR_CONSTANTE = "MODIFICAR_CONSTANTE";
    public static final String MODIFICAR_DEFERENCIACION = "MODIFICAR_DEFERENCIACION";
    public static final String OUR_SIN_PAQUETE = "OUR_SIN_PAQUETE";
    public static final String VARIABLE_YA_DECLARADA = "VARIABLE_YA_DECLARADA";
    public static final String VARIABLE_SOBREESCRITA = "VARIABLE_SOBREESCRITA";
    public static final String FUNCION_SOBREESCRITA = "FUNCION_SOBREESCRITA";
    public static final String DECLACION_CONSTANTE = "DECLACION_CONSTANTE";
    public static final String TIPO_FOREACH = "TIPO_FOREACH";
    public static final String ACCESO_DECLARACION = "ACCESO_DECLARACION";
    public static final String VARIABLE_SIN_TIPO = "VARIABLE_SIN_TIPO";
    public static final String VARIABLE_NO_EXISTE = "VARIABLE_NO_EXISTE";
    public static final String FUNCION_NO_EXISTE = "FUNCION_NO_EXISTE";
    public static final String PAQUETE_NO_EXISTE = "PAQUETE_NO_EXISTE";
    public static final String ETIQUETA_SIN_USO = "ETIQUETA_SIN_USO";
    public static final String TIPO_INCORRECTO = "TIPO_INCORRECTO";
    public static final String VARIABLE_ENMASCARADA = "VARIABLE_ENMASCARADA";
    public static final String ERROR_CASTING = "ERROR_CASTING";
    public static final String UNBOXING_SIN_TIPO = "UNBOXING_SIN_TIPO";
    public static final String ACCESO_NO_COLECCION = "ACCESO_NO_COLECCION";
    public static final String ACCESO_SIN_CONTEXTO = "ACCESO_SIN_CONTEXTO";
    public static final String ACCESO_ERRONEO = "ACCESO_ERRONEO";
    public static final String ACCESO_VACIO_COLECCION = "ACCESO_VACIO_COLECCION";
    public static final String ACCESO_VACIO_REFERENCIA = "ACCESO_VACIO_REFERENCIA";
    public static final String ACCESO_NO_REF = "ACCESO_NO_REF";
    public static final String ACCESO_REF_MULTIPLE = "ACCESO_REF_MULTIPLE";
    public static final String ACCESO_REF_ESCALAR = "ACCESO_REF_ESCALAR";
    public static final String ACCESO_ANIDADO_PORCENTAJE = "ACCESO_ANIDADO_PORCENTAJE";
    public static final String VARIABLE_ERROR_SIGIL = "VARIABLE_ERROR_SIGIL";
    public static final String REFERENCIA_NO_COLECCION = "REFERENCIA_NO_COLECCION";
    public static final String MAPA_NO_VALUE = "MAPA_NO_VALUE";
    public static final String ARRAY_INICIALIZACION_TAM = "ARRAY_INICIALIZACION_TAM";
    public static final String INICIALIZACION_SOLO_COLECIONES = "INICIALIZACION_SOLO_COLECIONES";
    public static final String REGEX_NO_VARIABLE = "REGEX_NO_VARIABLE";
    public static final String REGEX_MODIFICADOR_DESCONOCIDO = "REGEX_MODIFICADOR_DESCONOCIDO";
    public static final String RETURN_SIN_FUNCION = "RETURN_SIN_FUNCION";
    public static final String NEXT_LAST_SIN_BUCLE = "NEXT_LAST_SIN_BUCLE";
    public static final String SENTENCIA_INALCANZABLE = "SENTENCIA_INALCANZABLE";
    public static final String OPERADOR_SMART = "OPERADOR_SMART";
    public static final String MODULO_NO_VACIO = "MODULO_NO_VACIO";
    public static final String MODULO_YA_CREADO = "MODULO_YA_CREADO";
    public static final String MODULO_NO_EXISTE = "MODULO_NO_EXISTE";
    public static final String FUNCION_SIN_HANDLE = "FUNCION_SIN_HANDLE";
    public static final String DOBLE_DECLARACION = "DOBLE_DECLARACION";
    public static final String DECLARACION_NO_VAR = "DECLARACION_NO_VAR";
    public static final String CONTEXTO_HASH_ESCRITURA = "CONTEXTO_HASH_ESCRITURA";
    public static final String FUNCION_VARIABLE = "FUNCION_VARIABLE";
    public static final String FUNCION_VARIABLE_TIPO = "FUNCION_VARIABLE_TIPO";
    public static final String FUNCION_NUM_ARGS = "FUNCION_NUM_ARGS";
    public static final String SORT_MULTI_COLECCION = "SORT_MULTI_COLECCION";
    public static final String DELETE_NO_ACCESO = "DELETE_NO_ACCESO";
    public static final String DECLARACION_CONDICIONAL = "DECLARACION_CONDICIONAL";
    public static final String FLUJO_FUERA_COMPORTAMIENTO = "FLUJO_FUERA_COMPORTAMIENTO";
    public static final String MAIN_EXISTENTE = "MAIN_EXISTENTE";
    public static final String ESPECIAL_LOCAL = "ESPECIAL_LOCAL";
    public static final String ESPECIAL_CABECERA = "ESPECIAL_CABECERA";
    public static final String BLOQUE_INCOMPATIBLE = "BLOQUE_INCOMPATIBLE";
    public static final String BLOQUE_ESP_EN_USO = "BLOQUE_ESP_EN_USO"; 
    public static final String ESPECIAL_REPETIDO = "ESPECIAL_REPETIDO";
    public static final String REDUCCER_INVALIDO = "REDUCCER_INVALIDO";
    public static final String REDUCCER_DEPENDENCIAS = "REDUCCER_DEPENDENCIAS";

    public Errores() {
        super("errores");
    }

}
