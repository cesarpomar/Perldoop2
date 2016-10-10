package perldoop.modelo.arbol;

import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.funcion.Funcion;

/**
 * Interfaz para el patron visitor de las funciones nativas
 *
 * @author César Pomar
 */
public interface FuncionNativa {

    /**
     * Visita una funcion nativa
     *
     * @param f Función
     * @param args Argumentos
     */
    void visitar(Funcion f, Coleccion args);

}
