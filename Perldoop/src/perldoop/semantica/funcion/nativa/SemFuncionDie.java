package perldoop.semantica.funcion.nativa;

import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;

/**
 * Semantica función die
 *
 * @author César Pomar
 */
public final class SemFuncionDie extends SemFuncionNativa {

    public SemFuncionDie(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        checkArgumentos(f, 1, null);
        f.setTipo(new Tipo(Tipo.BOOLEAN));
    }

}
