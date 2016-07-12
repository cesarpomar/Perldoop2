package perldoop.generacion.rango;

import perldoop.modelo.arbol.rango.Rango;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de rangos
 *
 * @author César Pomar
 */
public class GenRango {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenRango(TablaGenerador tabla) {
        this.tabla = tabla;
    }
    
    public void visitar(Rango s){
        
    }
}
