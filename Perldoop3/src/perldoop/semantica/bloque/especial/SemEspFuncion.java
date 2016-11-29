package perldoop.semantica.bloque.especial;

import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de funciones especiales
 *
 * @author César Pomar
 */
public final class SemEspFuncion extends SemEspecial {

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemEspFuncion(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(Bloque s) {
        checkFlujo(s);
    }

}
