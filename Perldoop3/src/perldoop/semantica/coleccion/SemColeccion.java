package perldoop.semantica.coleccion;

import perldoop.modelo.arbol.coleccion.*;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de Coleccion
 *
 * @author César Pomar
 */
public class SemColeccion {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemColeccion(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(ColParentesis s) {
    }

    public void visitar(ColCorchete s) {
    }

    public void visitar(ColLlave s) {
    }

    public void visitar(ColGenerador s) {
    }

    public void visitar(ColMy s) {
    }

    public void visitar(ColOur s) {
    }
}
