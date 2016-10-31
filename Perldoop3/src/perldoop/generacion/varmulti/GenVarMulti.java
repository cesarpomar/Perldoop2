package perldoop.generacion.varmulti;

import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.arbol.varmulti.VarMulti;
import perldoop.modelo.arbol.varmulti.VarMultiMy;
import perldoop.modelo.arbol.varmulti.VarMultiOur;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.util.Buscar;

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

    /**
     * Concatena las declaraciones
     *
     * @param v Multi variable
     * @return Codigo
     */
    public StringBuilder concat(VarMulti v) {
        StringBuilder codigo = new StringBuilder(100);
        if(v.getTipo()==null){
            //TODO
        }else{
            //TODO
        }
        throw new UnsupportedOperationException("Not supported yet."); 
        //return codigo;
    }

    public void visitar(VarMultiMy s) {
        s.setCodigoGenerado(concat(s));
    }

    public void visitar(VarMultiOur s) {
        s.setCodigoGenerado(concat(s));
    }

}
