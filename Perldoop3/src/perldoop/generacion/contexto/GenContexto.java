package perldoop.generacion.contexto;

import perldoop.modelo.arbol.contexto.Contexto;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de contexto
 *
 * @author César Pomar
 */
public class GenContexto {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenContexto(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Contexto s) {
        //Sus padres generan el codigo por el
    }

}
