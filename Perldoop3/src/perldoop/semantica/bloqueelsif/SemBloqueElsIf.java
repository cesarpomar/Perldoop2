package perldoop.semantica.bloqueelsif;

import perldoop.modelo.arbol.bloqueelsif.BloqueElsIf;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de bloqueElsIf
 *
 * @author César Pomar
 */
public class SemBloqueElsIf {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemBloqueElsIf(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(BloqueElsIf s) {
    }
}
