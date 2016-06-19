package perldoop.generacion.variable;

import perldoop.generacion.TablaGenerador;
import perldoop.modelo.arbol.variable.VarExistente;
import perldoop.modelo.arbol.variable.VarMy;
import perldoop.modelo.arbol.variable.VarOur;
import perldoop.modelo.arbol.variable.VarPaquete;
import perldoop.util.Util;

/**
 * Clase generadora de variable
 * @author César Pomar
 */
public class GenVariable {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenVariable(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(VarExistente s) {
        s.setCodigoGenerado(s.getVar().getCodigoGenerado());
        Util.limpiar(s);
    }

    public void visitar(VarPaquete s) {
        StringBuilder c = s.getAmbito().getCodigoGenerado();
        c.append('.').append(s.getVar().getCodigoGenerado());
        s.setCodigoGenerado(c);
        Util.limpiar(s);
    }

    public void visitar(VarMy s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(VarOur s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
