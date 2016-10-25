package perldoop.traductor;

import perldoop.modelo.arbol.Simbolo;

/**
 * Clase que representa las acciones durante la traducción
 *
 * @author César pomar
 */
public interface Acciones {

    /**
     * Vuelve a analizar el simbolo despues de analizar s
     *
     * @param s Simbolo anterior al reanalisis
     */
    void reAnalizarDespuesDe(Simbolo s);

    /**
     * Salta la etapa de generación del simbolo actual
     */
    void saltarGenerador();

}
