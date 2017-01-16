package perldoop.semantica.funcion.nativa;

import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;

/**
 * Semantica función close
 *
 * @author César Pomar
 */
public final class SemFuncionClose extends SemFuncionNativa {

    public SemFuncionClose(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        checkArgumentos(f, 1, 1);
        checkVariable(f.getColeccion().getLista().getExpresiones().get(0), new Tipo(Tipo.FILE));
        f.setTipo(new Tipo(Tipo.INTEGER));
    }

}
