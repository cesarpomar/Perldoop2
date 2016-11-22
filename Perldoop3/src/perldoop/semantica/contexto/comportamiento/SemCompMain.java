package perldoop.semantica.contexto.comportamiento;

import perldoop.modelo.arbol.contexto.Contexto;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de main
 *
 * @author César Pomar
 */
public final class SemCompMain extends SemComportamiento {

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemCompMain(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(Contexto s) {
        throw new UnsupportedOperationException("Main, Not supported yet.");
    }



}
