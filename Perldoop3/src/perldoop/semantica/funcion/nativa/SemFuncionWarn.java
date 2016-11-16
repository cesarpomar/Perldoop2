package perldoop.semantica.funcion.nativa;

import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;

/**
 * Semantica función warn
 *
 * @author César Pomar
 */
public final class SemFuncionWarn extends SemFuncionNativa {

    public SemFuncionWarn(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        checkArgumentos(f, 1, null);
        f.setTipo(new Tipo(Tipo.BOOLEAN));
    }

}
