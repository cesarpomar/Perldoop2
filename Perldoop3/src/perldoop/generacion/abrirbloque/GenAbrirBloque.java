package perldoop.generacion.abrirbloque;

import perldoop.generacion.TablaGenerador;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;

/**
 * Clase generadora de abrirBloque
 * @author César Pomar
 */
public class GenAbrirBloque {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenAbrirBloque(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(AbrirBloque s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
