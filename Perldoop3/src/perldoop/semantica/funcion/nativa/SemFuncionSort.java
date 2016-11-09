package perldoop.semantica.funcion.nativa;

import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.arbol.funcion.FuncionBloque;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Semantica función sort
 *
 * @author César Pomar
 */
public final class SemFuncionSort extends SemFuncionNativa {

    public SemFuncionSort(TablaSemantica tabla) {
        super(tabla);
    }

    /**
     * Valida el tipo de sort
     *
     * @param col Coleccion a ordenar
     */
    private void checkTipo(Coleccion col) {
        Tipo t = col.getTipo();
        if (t.isColeccion() && t.getSubtipo(1).isColeccion()) {
            tabla.getGestorErrores().error(Errores.SORT_MULTI_COLECCION, Buscar.tokenInicio(col));
            throw new ExcepcionSemantica(Errores.SORT_MULTI_COLECCION);
        }
    }

    @Override
    public void visitar(FuncionBasica f) {
        checkTipo(f.getColeccion());
        f.setTipo(f.getColeccion().getTipo());
    }

    @Override
    public void visitar(FuncionBloque f) {
        checkTipo(f.getColeccion());
        f.setTipo(f.getColeccion().getTipo());

    }

}
