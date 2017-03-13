package perldoop.semantica.funcion.nativa;

import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;

/**
 * Semantica función exit
 *
 * @author César Pomar
 */
public final class SemFuncionExit extends SemFuncionNativa {

    public SemFuncionExit(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        checkArgumentos(f, null, 1);
        f.setTipo(new Tipo(Tipo.BOOLEAN));
    }

}
