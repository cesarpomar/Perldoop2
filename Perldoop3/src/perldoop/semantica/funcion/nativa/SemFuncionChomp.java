package perldoop.semantica.funcion.nativa;

import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;

/**
 * Semantica función print
 * @author César Pomar
 */
public final class SemFuncionChomp extends SemFuncionNativa{

    public SemFuncionChomp(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        checkArgumentos(f, 1);
        checkVariable(f.getColeccion().getLista().getExpresiones().get(0), new Tipo(Tipo.STRING));
        f.setTipo(new Tipo(Tipo.INTEGER));
    }

     
}
