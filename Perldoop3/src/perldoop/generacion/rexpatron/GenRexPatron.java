package perldoop.generacion.rexpatron;

import perldoop.modelo.arbol.rexpatron.RexPatron;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de rexPatron
 *
 * @author César Pomar
 */
public class GenRexPatron {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenRexPatron(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(RexPatron s) {

    }
}
