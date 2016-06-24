package perldoop.generacion.funcion;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.funcion.Argumentos;
import perldoop.modelo.arbol.funcion.FuncionArgs;
import perldoop.modelo.arbol.funcion.FuncionDo;
import perldoop.modelo.arbol.funcion.FuncionNoArgs;
import perldoop.modelo.arbol.funcion.FuncionPaqueteArgs;
import perldoop.modelo.arbol.funcion.FuncionPaqueteNoArgs;

/**
 * Clase generadora de funcion
 * @author César Pomar
 */
public class GenFuncion {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenFuncion(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(FuncionPaqueteArgs s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(FuncionPaqueteNoArgs s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(FuncionArgs s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(FuncionNoArgs s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(FuncionDo s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(Argumentos s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
