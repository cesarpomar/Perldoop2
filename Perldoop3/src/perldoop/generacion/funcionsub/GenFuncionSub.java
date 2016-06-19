package perldoop.generacion.funcionsub;

import perldoop.generacion.TablaGenerador;
import perldoop.modelo.arbol.funcionsub.FuncionSub;

/**
 * Clase generadora de funcionSub
 * @author César Pomar
 */
public class GenFuncionSub {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenFuncionSub(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(FuncionSub s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
