package perldoop.generacion.lectura;

import perldoop.modelo.arbol.lectura.LecturaFile;
import perldoop.modelo.arbol.lectura.LecturaIn;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de lectura
 *
 * @author César Pomar
 */
public class GenLectura {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenLectura(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(LecturaIn s) {
    }

    public void visitar(LecturaFile s) {
    }

}
