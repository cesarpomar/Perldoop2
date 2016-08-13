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
     * Analiza un simbolo
     *
     * @param s Simbolo
     */
    void analizar(Simbolo s) throws ExcepcionSemantica;

    /**
     * Vuelve a analizar el simbolo despues de analizar s
     *
     * @param s Simbolo anterior al reanalisis
     */
    void reAnalizarDespuesDe(Simbolo s);

}
