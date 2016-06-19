package perldoop.generacion.lista;

import perldoop.generacion.TablaGenerador;
import perldoop.modelo.arbol.lista.Lista;

/**
 * Clase generadora de lista
 * @author César Pomar
 */
public class GenLista {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenLista(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Lista s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
