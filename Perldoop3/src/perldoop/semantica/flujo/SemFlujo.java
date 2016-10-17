package perldoop.semantica.flujo;

import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.arbol.bloque.BloqueIf;
import perldoop.modelo.arbol.bloque.BloqueUnless;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.flujo.*;
import perldoop.modelo.arbol.funciondef.FuncionDef;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.util.Buscar;

/**
 * Clase para la semantica de flujo
 *
 * @author César Pomar
 */
public class SemFlujo {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemFlujo(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    /**
     * Comprueba si el simbolo esta dentro de un bucle
     *
     * @param s Simbolo
     * @param t Terminal para error
     */
    private void isBucle(Flujo s, Terminal t) {
        Bloque bloque = Buscar.buscarPadre(s, Bloque.class);
        if (bloque == null || bloque instanceof BloqueIf || bloque instanceof BloqueUnless) {
            tabla.getGestorErrores().error(Errores.NEXT_LAST_SIN_BUCLE, t.getToken());
            throw new ExcepcionSemantica(Errores.NEXT_LAST_SIN_BUCLE);
        }
    }

    /**
     * Comprueba que no se genera codigo muerto
     *
     * @param s Simbolo
     */
    private void codigoMuerto(Flujo s) {
        //TODO
    }

    public void visitar(Next s) {
        isBucle(s, s.getNext());
        codigoMuerto(s);
    }

    public void visitar(Last s) {
        isBucle(s, s.getLast());
        codigoMuerto(s);
    }

    public void visitar(Return s) {
        FuncionDef funcion = Buscar.buscarPadre(s, FuncionDef.class);
        if (funcion == null) {
            tabla.getGestorErrores().error(Errores.NEXT_LAST_SIN_BUCLE, s.getId().getToken());
            throw new ExcepcionSemantica(Errores.NEXT_LAST_SIN_BUCLE);
        }
        codigoMuerto(s);
    }
}
