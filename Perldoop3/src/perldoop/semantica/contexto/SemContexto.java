package perldoop.semantica.contexto;

import perldoop.modelo.arbol.contexto.Contexto;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de cntexto
 *
 * @author César Pomar
 */
public class SemContexto {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemContexto(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(Contexto s) {
    }

}
