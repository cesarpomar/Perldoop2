package perldoop.generacion.elsif;

import perldoop.generacion.TablaGenerador;
import perldoop.modelo.arbol.elsif.ElsIfElsIf;
import perldoop.modelo.arbol.elsif.ElsIfElse;
import perldoop.modelo.arbol.elsif.ElsIfNada;

/**
 * Clase generadora de elsif
 * @author César Pomar
 */
public class GenElsIf {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenElsIf(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(ElsIfNada s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ElsIfElsIf s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ElsIfElse s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
