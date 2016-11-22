package perldoop.semantica.contexto.comportamiento;

import perldoop.modelo.arbol.contexto.Contexto;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de hadoop
 *
 * @author César Pomar
 */
public final class SemCompHadoop extends SemComportamiento {

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemCompHadoop(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(Contexto s) {
        throw new UnsupportedOperationException("Hadoop, Not supported yet.");
    }

}
