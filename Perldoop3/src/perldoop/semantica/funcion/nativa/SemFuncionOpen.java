package perldoop.semantica.funcion.nativa;

import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;

/**
 * Semantica función open
 *
 * @author César Pomar
 */
public final class SemFuncionOpen extends SemFuncionNativa {

    public SemFuncionOpen(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        checkArgumentos(f, 1, 3);
        checkVariable(f.getColeccion().getLista().getExpresiones().get(0), new Tipo(Tipo.FILE));
        f.setTipo(new Tipo(Tipo.INTEGER));
    }

}
