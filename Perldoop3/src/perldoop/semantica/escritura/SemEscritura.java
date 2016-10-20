package perldoop.semantica.escritura;

import perldoop.modelo.arbol.escritura.*;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de escritura
 *
 * @author César Pomar
 */
public class SemEscritura {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemEscritura(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(EscrituraErr s) {
    }

    public void visitar(EscrituraOut s) {
    }

}
