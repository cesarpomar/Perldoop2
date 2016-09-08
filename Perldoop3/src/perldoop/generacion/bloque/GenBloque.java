package perldoop.generacion.bloque;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.bloque.BloqueCondicional;
import perldoop.modelo.arbol.bloque.BloqueDoUntil;
import perldoop.modelo.arbol.bloque.BloqueDoWhile;
import perldoop.modelo.arbol.bloque.BloqueFor;
import perldoop.modelo.arbol.bloque.BloqueForeach;
import perldoop.modelo.arbol.bloque.BloqueForeachVar;
import perldoop.modelo.arbol.bloque.BloqueUntil;
import perldoop.modelo.arbol.bloque.BloqueWhile;

/**
 * Clase generadora de bloque
 * @author César Pomar
 */
public class GenBloque {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenBloque(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(BloqueCondicional s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(BloqueWhile s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(BloqueUntil s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(BloqueDoWhile s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(BloqueDoUntil s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(BloqueFor s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(BloqueForeachVar s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(BloqueForeach s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
