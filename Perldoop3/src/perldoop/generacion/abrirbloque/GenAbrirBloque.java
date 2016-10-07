package perldoop.generacion.abrirbloque;

import perldoop.modelo.generacion.TablaGenerador;
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
     * @param tabla Tabla
     */
    public GenAbrirBloque(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(AbrirBloque s) {
        
    }

}
