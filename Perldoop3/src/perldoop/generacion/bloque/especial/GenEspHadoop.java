package perldoop.generacion.bloque.especial;

import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de bloque hadoop
 *
 * @author César Pomar
 */
public final class GenEspHadoop extends GenEspecial {

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenEspHadoop(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(Bloque s) {

    }

}
