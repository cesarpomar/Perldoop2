package perldoop.generacion.funciondef;

import perldoop.generacion.TablaGenerador;
import perldoop.modelo.arbol.funciondef.FuncionDef;

/**
 * Clase generadora de funcionDef
 * @author César Pomar
 */
public class GenFuncionDef {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenFuncionDef(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(FuncionDef s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
