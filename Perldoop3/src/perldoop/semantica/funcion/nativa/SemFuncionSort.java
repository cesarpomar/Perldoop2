package perldoop.semantica.funcion.nativa;

import java.util.List;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.Funcion;
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
    private void setTipo(Funcion f) {
        checkArgumentos(f, 1, null);
        List<Expresion> exps = Buscar.getExpresiones(f.getColeccion());
        if (exps.size() > 1) {
            f.setTipo(f.getColeccion().getTipo());
        } else {
            f.setTipo(exps.get(0).getTipo());
        }
    }

    @Override
    public void visitar(FuncionBasica f) {
        setTipo(f);
        Tipo t = f.getTipo();
        if (t.isColeccion() && t.getSubtipo(1).isColeccion()) {
            tabla.getGestorErrores().error(Errores.SORT_MULTI_COLECCION, Buscar.tokenInicio(f.getColeccion()));
            throw new ExcepcionSemantica(Errores.SORT_MULTI_COLECCION);
        }
    }

    @Override
    public void visitar(FuncionBloque f) {
        setTipo(f);
    }

}
