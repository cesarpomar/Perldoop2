package perldoop.generacion.escritura;

import perldoop.modelo.arbol.escritura.*;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de escritura
 *
 * @author César Pomar
 */
public class GenEscritura {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenEscritura(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(EscrituraOut s) {
    }

    public void visitar(EscrituraErr s) {
    }

}
