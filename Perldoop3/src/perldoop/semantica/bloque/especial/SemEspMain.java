package perldoop.semantica.bloque.especial;

import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de main
 *
 * @author César Pomar
 */
public final class SemEspMain extends SemEspecial {

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemEspMain(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(Bloque s) {
        throw new UnsupportedOperationException("Main, Not supported yet.");
    }



}
