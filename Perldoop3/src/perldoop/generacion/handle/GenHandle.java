package perldoop.generacion.handle;

import perldoop.modelo.arbol.handle.*;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de lectura
 *
 * @author César Pomar
 */
public class GenHandle {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenHandle(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(HandleOut s) {
        s.setCodigoGenerado(new StringBuilder("PerlFile.STDOUT"));
    }

    public void visitar(HandleErr s) {
        s.setCodigoGenerado(new StringBuilder("PerlFile.STDERR"));
    }

    public void visitar(HandleFile s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
