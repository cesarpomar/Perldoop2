package perldoop.generacion.funcion.nativa;

import perldoop.generacion.util.ColIterator;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.arbol.funcion.FuncionBloque;
import perldoop.modelo.arbol.funcion.FuncionHandle;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Generador de la funcion print
 *
 * @author César Pomar
 */
public class GenFuncionSort extends GenFuncionNativa {

    public GenFuncionSort(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visitar(FuncionBloque f) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
