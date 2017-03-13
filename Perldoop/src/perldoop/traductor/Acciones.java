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

    /**
     * La traduccion actual se detiene y se inicia el traductor del siguiente fichero. La traducción volvera a
     * reanudarse cuando el resto de traductores terminen o invoquen esta función. Si todos los traductores invocan la
     * función la siguiente invocación no tendra ningun efecto.
     *
     * @return True si el proceso de traducción se detiene, false si no es posible detener la traducción.
     */
    boolean detenerTraductor();

}
