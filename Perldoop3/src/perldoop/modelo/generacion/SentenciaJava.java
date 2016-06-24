package perldoop.modelo.generacion;

/**
 * Clase que representa una sentencia java
 * @author César Pomar
 */
public final class SentenciaJava implements CodigoJava {

    private String sentencia;

    /**
     * Construye la sentencia
     * @param sentencia Sentencia
     */
    public SentenciaJava(String sentencia) {
        this.sentencia = sentencia;
    }

    /**
     * Obtiene la sentencia
     * @return Sentencia
     */
    public String getSentencia() {
        return sentencia;
    }

    /**
     * Establece la sentencia
     * @param sentencia Sentencia
     */
    public void setSentencia(String sentencia) {
        this.sentencia = sentencia;
    }

    @Override
    public String toString() {
        return sentencia;
    }

}
