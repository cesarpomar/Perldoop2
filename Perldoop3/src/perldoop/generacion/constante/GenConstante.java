package perldoop.generacion.constante;

import perldoop.generacion.TablaGenerador;
import perldoop.modelo.arbol.constante.CadenaComando;
import perldoop.modelo.arbol.constante.CadenaDoble;
import perldoop.modelo.arbol.constante.CadenaSimple;
import perldoop.modelo.arbol.constante.Decimal;
import perldoop.modelo.arbol.constante.Entero;

/**
 * Clase generadora de constante
 * @author César Pomar
 */
public class GenConstante {
    
    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenConstante(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Entero s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(Decimal s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CadenaSimple s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CadenaDoble s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(CadenaComando s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
