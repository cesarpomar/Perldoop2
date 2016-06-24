package perldoop.generacion.bloqueelsif;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.bloqueelsif.BloqueElsIf;

/**
 * Clase generadora de bloqueElsIf
 * @author César Pomar
 */
public class GenBloqueElsIf {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenBloqueElsIf(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(BloqueElsIf s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
