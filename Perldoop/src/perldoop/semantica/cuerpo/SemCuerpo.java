package perldoop.semantica.cuerpo;

import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de cuerpo
 *
 * @author César Pomar
 */
public class SemCuerpo {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemCuerpo(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(Cuerpo s) {
    }
}
