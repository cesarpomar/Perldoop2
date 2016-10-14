package perldoop.generacion.lista;

import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de lista
 *
 * @author César Pomar
 */
public class GenLista {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenLista(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Lista s) {
    }

}
