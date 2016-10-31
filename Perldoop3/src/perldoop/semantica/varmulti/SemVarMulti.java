package perldoop.semantica.varmulti;

import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.expresion.ExpVariable;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.arbol.variable.VarMy;
import perldoop.modelo.arbol.variable.VarOur;
import perldoop.modelo.arbol.varmulti.VarMulti;
import perldoop.modelo.arbol.varmulti.VarMultiMy;
import perldoop.modelo.arbol.varmulti.VarMultiOur;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Clase para la semantica de multiples variables en declaración
 *
 * @author César Pomar
 */
public class SemVarMulti {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemVarMulti(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    /**
     * Comprueba que en la lista solo haya variables y ninguna este acompañada de my o our
     *
     * @param s Simbolo s
     */
    private void checkVariables(VarMulti s) {
        for (Expresion exp : s.getLista().getExpresiones()) {
            if (exp instanceof ExpVariable) {
                if (((ExpVariable) exp).getVariable() instanceof VarMy || ((ExpVariable) exp).getVariable() instanceof VarOur) {
                    tabla.getGestorErrores().error(Errores.DOBLE_DECLARACION, Buscar.tokenInicio(exp));
                    throw new ExcepcionSemantica(Errores.DOBLE_DECLARACION);
                }
            } else {
                tabla.getGestorErrores().error(Errores.DECLARACION_NO_VAR, Buscar.tokenInicio(exp));
                throw new ExcepcionSemantica(Errores.DECLARACION_NO_VAR);
            }
        }
    }

    /**
     * Establece el tipo
     *
     * @param s Simbolo s
     */
    private void setTipo(VarMulti s) {
        if(!(Buscar.getPadre(s, 2) instanceof StcLista)){
            Tipo t = s.getLista().getExpresiones().get(0).getTipo().getSubtipo(0).add(0, Tipo.ARRAY);
            s.setTipo(t);
        }
    }

    public void visitar(VarMultiMy s) {
        checkVariables(s);
        setTipo(s);
    }

    public void visitar(VarMultiOur s) {
        checkVariables(s);
        setTipo(s);
    }
}
