package perldoop.generacion.bloque.especial;

import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase para la generacion de bloques especiales
 *
 * @author César Pomar
 */
public abstract class GenEspecial {

    protected TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenEspecial(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    /**
     * Visita un bloque
     *
     * @param s Bloque
     */
    public abstract void visitar(Bloque s);

}
