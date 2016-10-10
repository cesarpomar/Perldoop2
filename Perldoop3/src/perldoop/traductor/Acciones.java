package perldoop.traductor;

import perldoop.excepciones.ExcepcionSemantica;
import perldoop.modelo.arbol.Simbolo;

/**
 * Clase que representa las acciones durante la traducción
 *
 * @author César pomar
 */
public interface Acciones {

    /**
     * Analiza un simbolo.
     *
     * @param s Simbolo
     */
    void analizar(Simbolo s) throws ExcepcionSemantica;

    /**
     * Vuelve a analizar un simbolo y todos los simbolos ya analizados que dependen de el.
     *
     * @param s Simbolo
     */
    void reAnalizar(Simbolo s) throws ExcepcionSemantica;

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
