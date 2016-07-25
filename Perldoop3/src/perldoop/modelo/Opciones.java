package perldoop.modelo;

import java.io.File;

/**
 * Clase para almacenar las opciones
 *
 * @author César Pomar
 */
public class Opciones {

    private boolean sentenciasEstrictas;
    private File DirectorioSalida;
    private boolean formatearCodigo;
    private boolean comentarios;

    /**
     * Obtiene si las sentencias son estrictas
     *
     * @return Sentencias estrictas
     */
    public boolean isSentenciasEstrictas() {
        return sentenciasEstrictas;
    }

    /**
     * Establece si las sentencias son estrictas
     *
     * @param sentenciasEstrictas Sentencias estrictas
     */
    public void setSentenciasEstrictas(boolean sentenciasEstrictas) {
        this.sentenciasEstrictas = sentenciasEstrictas;
    }

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
     * Establece la traducción de comentarios
     *
     * @return Traducción de comentarios
     */
    public boolean isComentarios() {
        return comentarios;
    }

    /**
     * Obtiene la traducción de comentarios
     *
     * @param comentarios Traducción de comentarios
     */
    public void setComentarios(boolean comentarios) {
        this.comentarios = comentarios;
    }

}
