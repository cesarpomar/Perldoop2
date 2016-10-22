package perldoop.util;

/**
 * Utilidades varias para servir de ayuda a todos los modulos
 *
 * @author César Pomar
 */
public final class Utiles {

    /**
     * Normaliza un nombre para poder ser usado como identificador en java
     *
     * @param nombre Nombre
     * @return Identificador
     */
    public static String normalizar(String nombre) {
        nombre = nombre.replaceAll("_", "__");
        return nombre.replaceAll("(^[0-9]|[^a-zA-Z0-9])", "_");
    }

}
