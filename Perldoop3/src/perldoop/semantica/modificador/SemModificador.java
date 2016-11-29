package perldoop.semantica.modificador;

import java.util.List;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.coleccion.ColDec;
import perldoop.modelo.arbol.modificador.*;
import perldoop.modelo.arbol.variable.VarMy;
import perldoop.modelo.arbol.variable.VarOur;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.util.Buscar;

/**
 * Clase para la semantica de modificador
 *
 * @author César Pomar
 */
public class SemModificador {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemModificador(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    /**
     * Comprueba la existencia de delcaraciondes dentro de sentencias condicionadas
     *
     * @param m Modificador
     */
    private void checkDeclaraciones(Modificador m) {
        List<Simbolo> lista = Buscar.buscarClases(m.getPadre(), VarMy.class, VarOur.class, ColDec.class);
        if (!lista.isEmpty()) {
            tabla.getGestorErrores().error(Errores.DECLARACION_CONDICIONAL, Buscar.tokenInicio(lista.get(0)));
            throw new ExcepcionSemantica(Errores.DECLARACION_CONDICIONAL);
        }
    }

    public void visitar(ModNada s) {
    }

    public void visitar(ModIf s) {
        checkDeclaraciones(s);
    }

    public void visitar(ModUnless s) {
        checkDeclaraciones(s);
    }

    public void visitar(ModWhile s) {
        checkDeclaraciones(s);
    }

    public void visitar(ModUntil s) {
        checkDeclaraciones(s);
    }

    public void visitar(ModFor s) {
        checkDeclaraciones(s);
    }
}
