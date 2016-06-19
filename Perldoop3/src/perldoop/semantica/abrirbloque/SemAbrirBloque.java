package perldoop.semantica.abrirbloque;

import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.semantica.TablaSemantica;

/**
 * Clase para la semantica de abrirBloque
 *
 * @author César Pomar
 */
public class SemAbrirBloque {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemAbrirBloque(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(AbrirBloque s) {
    }
}
