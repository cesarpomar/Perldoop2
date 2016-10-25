package perldoop.generacion.lectura;

import perldoop.modelo.arbol.lectura.LecturaFile;
import perldoop.modelo.arbol.lectura.LecturaArg;
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

    public void visitar(LecturaArg s) {
    }

    public void visitar(LecturaFile s) {
    }

}
