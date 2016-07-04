package perldoop.semantica.variable;

import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.acceso.Acceso;
import perldoop.modelo.arbol.bloque.BloqueForeachVar;
import perldoop.modelo.arbol.variable.*;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.util.Buscar;

/**
 * Clase para la semantica de variable
 *
 * @author César Pomar
 */
public class SemVariable {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemVariable(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(VarExistente s) {
        s.setTipo(new Tipo());

    }

    public void visitar(VarPaquete s) {

    }

    public void visitar(VarMy s) {
        Simbolo uso = Buscar.getPadre(s, 1);
        //No se puede acceder en la declaracion
        if (uso instanceof Acceso) {
            tabla.getGestorErrores().error(Errores.ACCESO_DECLARACION, s.getVar().getToken());
            throw new ExcepcionSemantica();
        }
        Tipo t = tabla.getTablaSimbolos().getDeclaracion(s.getContexto().toString() + s.getVar().toString());
        //Si la varaible pertenece a un foreach no hace falta hacer nada
        if (uso instanceof BloqueForeachVar) {
            if (t != null) {
                tabla.getGestorErrores().error(Errores.AVISO, Errores.TIPO_FOREACH, s.getMy().getToken());
            }
            BloqueForeachVar foreach = (BloqueForeachVar) uso;
            if (foreach.getExpresion2().getTipo() == null) {
                tabla.getAcciones().reAnalizarDesdeDe(foreach.getExpresion2());
            } else {
                s.setTipo(new Tipo(foreach.getExpresion2().getTipo()));
            }

        } else if (t != null) {
            s.setTipo(t);
        } else {

        }
    }

    public void visitar(VarOur s) {
        if (!tabla.getTablaSimbolos().isPaquete()) {
            tabla.getGestorErrores().error(Errores.AVISO, Errores.OUR_SIN_PAQUETE, s.getOur().getToken());
        }

    }
}
