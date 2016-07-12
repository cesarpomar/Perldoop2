package perldoop.semantica.rango;

import perldoop.modelo.arbol.rango.Rango;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de rangos
 *
 * @author César Pomar
 */
public class SemRango {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemRango(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(Rango s) {

    }
}
