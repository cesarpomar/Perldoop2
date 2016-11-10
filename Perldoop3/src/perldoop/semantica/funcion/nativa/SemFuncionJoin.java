package perldoop.semantica.funcion.nativa;

import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;

/**
 * Semantica función join
 *
 * @author César Pomar
 */
public final class SemFuncionJoin extends SemFuncionNativa {

    public SemFuncionJoin(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        checkArgumentos(f, 2, null);
        f.setTipo(new Tipo(Tipo.STRING));
    }

}
