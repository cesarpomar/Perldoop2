package perldoop.generacion.varmulti;

import perldoop.modelo.arbol.varmulti.VarMultiMy;
import perldoop.modelo.arbol.varmulti.VarMultiOur;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de multiples variables en declaración
 *
 * @author César Pomar
 */
public class GenVarMulti {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenVarMulti(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(VarMultiMy s) {

    }

    public void visitar(VarMultiOur s) {

    }

}
