package perldoop.generacion.std;

import perldoop.modelo.arbol.std.StdOut;
import perldoop.modelo.arbol.std.StdErr;
import perldoop.modelo.arbol.std.StdIn;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de std
 *
 * @author César Pomar
 */
public class GenStd {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenStd(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(StdOut s) {
        s.setCodigoGenerado(new StringBuilder("PerlFile.STDOUT"));
    }

    public void visitar(StdErr s) {
        s.setCodigoGenerado(new StringBuilder("PerlFile.STDERR"));
    }

    public void visitar(StdIn s) {
        s.setCodigoGenerado(new StringBuilder("PerlFile.STDIN"));
    }

}
