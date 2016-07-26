package perldoop.generacion.flujo;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.flujo.Last;
import perldoop.modelo.arbol.flujo.Next;
import perldoop.modelo.arbol.flujo.Return;

/**
 * Clase generadora de flujo
 *
 * @author César Pomar
 */
public class GenFlujo {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenFlujo(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Next s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(Last s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(Return s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
