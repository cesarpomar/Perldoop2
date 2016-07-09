package perldoop.lib;

/**
 * Interfaz para contenedor de cualquier tipo de dato
 *
 * @author César Pomar
 */
public interface Box {

    /**
     * Obtiene la representanción entera
     *
     * @return Entero despues de la conversión
     */
    Integer intValue();

    /**
     * Obtiene la representación entero largo
     *
     * @return Entero largo despues de la conversión
     */
    Long longValue();

    /**
     * Obtiene la representación flotante
     *
     * @return Flotante despues de la conversión
     */
    Float floatValue();

    /**
     * Obtiene la representación flotante de doble precisión
     *
     * @return Flotante de doble precisión despues de la conversión
     */
    Double doubleValue();

    /**
     * Obtiene la representación cadena
     *
     * @return Cadena despues de la conversión
     */
    String stringValue();

    /**
     * Obtiene la representación referencia
     *
     * @param <T> Tipo referencia
     * @return Referencia despues de la conversión
     */
    <T> Ref<T> RefValue();

    /**
     * Obtiene la representación fichero
     *
     * @return Fichero despues de la conversión
     */
    PerlFile FileValue();
}
