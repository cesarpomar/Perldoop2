package perldoop.semantica.paquetes;

import perldoop.modelo.arbol.paquete.Paquetes;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de paquetes
 *
 * @author César Pomar
 */
public class SemPaquetes {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemPaquetes(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(Paquetes s) {
    }
}
