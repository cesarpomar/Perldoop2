package perldoop.modelo;

import java.io.File;

/**
 * Clase para almacenar las opciones
 *
 * @author César Pomar
 */
public class Opciones {

    //Argumentos opcionales
    private File DirectorioSalida;
    private boolean formatearCodigo;
    private boolean copiarComentarios;
    private boolean ocultarAvisos;
    private Integer mostrarErrores;
    private String codificacion;
    private String[] paquetes;
    private boolean libreria;
    private boolean jImporter;
    //Argumentos Optimización
    private boolean optNulos;
    private boolean optIntancias;
    private boolean optDiamante;
    private boolean optSentencias;
    private boolean optModulo;
    //Argumentos depuración
    private boolean depTokens;
    private boolean depTerminales;
    private boolean depTree;
    private boolean depTraduccion;
    private int depEtapas;

    /**
     * Obtiene el directorio de salida
     *
     * @return Directorio de salida
     */
    public File getDirectorioSalida() {
        return DirectorioSalida;
    }

    /**
     * Establece el directorio de salida
     *
     * @param DirectorioSalida Directorio de salida
     */
    public void setDirectorioSalida(File DirectorioSalida) {
        this.DirectorioSalida = DirectorioSalida;
    }

    /**
     * Obtiene si se debe formatear el código
     *
     * @return Formatear el código
     */
    public boolean isFormatearCodigo() {
        return formatearCodigo;
    }

    /**
     * Establece si se debe formatear el código
     *
     * @param formatearCodigo Formatear el código
     */
    public void setFormatearCodigo(boolean formatearCodigo) {
        this.formatearCodigo = formatearCodigo;
    }

    /**
     * Obtiene copiar comentarios
     *
     * @return Copiar comentario
     */
    public boolean isCopiarComentarios() {
        return copiarComentarios;
    }

    /**
     * Establece copiar comentarios
     *
     * @param copiarComentarios Copiar comentarios
     */
    public void setCopiarComentarios(boolean copiarComentarios) {
        this.copiarComentarios = copiarComentarios;
    }

    /**
     * Obtiene ocultar avisos
     *
     * @return Ocultar avisos
     */
    public boolean isOcultarAvisos() {
        return ocultarAvisos;
    }

    /**
     * Establece ocultar avisos
     *
     * @param ocultarAvisos Ocultar avisos
     */
    public void setOcultarAvisos(boolean ocultarAvisos) {
        this.ocultarAvisos = ocultarAvisos;
    }

    /**
     * Obtiene mostrar errores
     *
     * @return Mostar errores
     */
    public Integer getMostrarErrores() {
        return mostrarErrores;
    }

    /**
     * Establece mostrar errores
     *
     * @param mostrarErrores Mostrar errores
     */
    public void setMostrarErrores(Integer mostrarErrores) {
        this.mostrarErrores = mostrarErrores;
    }

    /**
     * Obtiene la codificacion
     *
     * @return Codificacion
     */
    public String getCodificacion() {
        return codificacion;
    }

    /**
     * Establece la codificacion
     *
     * @param codificacion Codificacion
     */
    public void setCodificacion(String codificacion) {
        this.codificacion = codificacion;
    }

    /**
     * Obtiene optimizar instancias
     *
     * @return Optimizar instancias
     */
    public boolean isOptIntancias() {
        return optIntancias;
    }

    /**
     * Establece optimizar instancias
     *
     * @param optIntancias Optimizar instancias
     */
    public void setOptIntancias(boolean optIntancias) {
        this.optIntancias = optIntancias;
    }

    /**
     * Obtiene optimizar diamante
     *
     * @return Optimizar diamante
     */
    public boolean isOptDiamante() {
        return optDiamante;
    }

    /**
     * Establece optimizar diamante
     *
     * @param optDiamante Optimizar diamante
     */
    public void setOptDiamante(boolean optDiamante) {
        this.optDiamante = optDiamante;
    }

    /**
     * Obtiene optimizar sentencias
     *
     * @return Optimizar sentencias
     */
    public boolean isOptSentencias() {
        return optSentencias;
    }

    /**
     * Establece optimizar sentencias
     *
     * @param optSentencias Optimizar sentencias
     */
    public void setOptSentencias(boolean optSentencias) {
        this.optSentencias = optSentencias;
    }

    /**
     * Obtiene depuracion tokens
     *
     * @return Depuracion tokens
     */
    public boolean isDepTokens() {
        return depTokens;
    }

    /**
     * Establece depuracion tokens
     *
     * @param depTokens Depuracion tokens
     */
    public void setDepTokens(boolean depTokens) {
        this.depTokens = depTokens;
    }

    /**
     * Obtiene depuracion terminales
     *
     * @return Depuracion terminales
     */
    public boolean isDepTerminales() {
        return depTerminales;
    }

    /**
     * Establece depuracion terminales
     *
     * @param depTerminales Depuracion terminales
     */
    public void setDepTerminales(boolean depTerminales) {
        this.depTerminales = depTerminales;
    }

    /**
     * Establece depuracion arbol
     *
     * @return Depuracion arbol
     */
    public boolean isDepTree() {
        return depTree;
    }

    /**
     * Obtiene depuracion arbol
     *
     * @param depTree Depuracion arbol
     */
    public void setDepTree(boolean depTree) {
        this.depTree = depTree;
    }

    /**
     * Obtiene depuracion traduccion
     *
     * @return Depuracion traduccion
     */
    public boolean isDepTraduccion() {
        return depTraduccion;
    }

    /**
     * Establece depuracion traduccion
     *
     * @param depTraduccion Depuracion traduccion
     */
    public void setDepTraduccion(boolean depTraduccion) {
        this.depTraduccion = depTraduccion;
    }

    /**
     * Obtiene depuracion etapas
     *
     * @return Depuracion etapas
     */
    public int getDepEtapas() {
        return depEtapas;
    }

    /**
     * Establece depuracion etapas
     *
     * @param depEtapas Depuracion etapas
     */
    public void setDepEtapas(int depEtapas) {
        this.depEtapas = depEtapas;
    }

    /**
     * Obtiene optimizar nulos
     *
     * @return Optimizar nulos
     */
    public boolean isOptNulos() {
        return optNulos;
    }

    /**
     * Establece optimizar nulos
     *
     * @param optNulos Optimizar nulos
     */
    public void setOptNulos(boolean optNulos) {
        this.optNulos = optNulos;
    }

    /**
     * Establece optimizar modulos
     *
     * @return Optimizar modulos
     */
    public boolean isOptModulo() {
        return optModulo;
    }

    /**
     * Obtiene ptimizar modulos
     *
     * @param optModulo Otimizar modulos
     */
    public void setOptModulo(boolean optModulo) {
        this.optModulo = optModulo;
    }

    /**
     * Obtiene los paquetes
     *
     * @return Paquetes
     */
    public String[] getPaquetes() {
        return paquetes;
    }

    /**
     * Establece los paquetes
     *
     * @param paquetes Paquetes
     */
    public void setPaquetes(String[] paquetes) {
        this.paquetes = paquetes;
    }

    /**
     * Obtiene generar libreria
     *
     * @return Generar libreria
     */
    public boolean isLibreria() {
        return libreria;
    }

    /**
     * Establece generar libreria
     *
     * @param libreria Generar libreria
     */
    public void setLibreria(boolean libreria) {
        this.libreria = libreria;
    }

    /**
     * Obtiene usar jImporter
     *
     * @return Usar jImporter
     */
    public boolean isjImporter() {
        return jImporter;
    }

    /**
     * Establece usar jImporter
     *
     * @param jImporter Usar jImporter
     */
    public void setjImporter(boolean jImporter) {
        this.jImporter = jImporter;
    }

}
