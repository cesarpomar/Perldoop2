package perldoop.semantica.funcion.nativa;

import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;

/**
 * Semantica función split
 *
 * @author César Pomar
 */
public final class SemFuncionSplit extends SemFuncionNativa {

    public SemFuncionSplit(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        checkArgumentos(f, 2, 3);
        f.setTipo(new Tipo(Tipo.ARRAY, Tipo.STRING));
    }

}
