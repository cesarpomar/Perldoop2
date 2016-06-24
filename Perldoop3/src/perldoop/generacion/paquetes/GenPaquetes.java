package perldoop.generacion.paquetes;

import perldoop.modelo.arbol.paquete.Paquetes;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de paquetes
 *
 * @author César Pomar
 */
public class GenPaquetes {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenPaquetes(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Paquetes s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
