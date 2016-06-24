package perldoop.generacion.cuerpo;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.cuerpo.Cuerpo;

/**
 * Clase generadora de cuerpo
 * @author César Pomar
 */
public class GenCuerpo {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenCuerpo(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Cuerpo s) {
        
    }

}
