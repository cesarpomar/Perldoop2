package perldoop.semantica.bloque.especial;

import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de hadoop
 *
 * @author César Pomar
 */
public final class SemEspHadoop extends SemEspecial {

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemEspHadoop(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(Bloque s) {
        throw new UnsupportedOperationException("Hadoop, Not supported yet.");
    }

}
