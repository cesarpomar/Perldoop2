package perldoop.semantica.funcion.nativa;

import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;

/**
 * Semantica función defined
 *
 * @author César Pomar
 */
public final class SemFuncionDefined extends SemFuncionNativa {

    public SemFuncionDefined(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        checkArgumentos(f, 1, 1);
        f.setTipo(new Tipo(Tipo.BOOLEAN));
    }

}
