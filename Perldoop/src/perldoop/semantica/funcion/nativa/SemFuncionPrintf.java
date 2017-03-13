package perldoop.semantica.funcion.nativa;

import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.arbol.funcion.FuncionBloque;
import perldoop.modelo.arbol.funcion.FuncionHandle;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.util.Tipos;

/**
 * Semantica función printf
 *
 * @author César Pomar
 */
public final class SemFuncionPrintf extends SemFuncionNativa {

    public SemFuncionPrintf(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        checkArgumentos(f, 1, null);
        f.setTipo(new Tipo(Tipo.INTEGER));
    }

    @Override
    public void visitar(FuncionBloque f) {
        checkArgumentos(f, 1, null);
        f.setTipo(new Tipo(Tipo.INTEGER));
        Tipos.casting(f.getExpresion(), new Tipo(Tipo.FILE), tabla.getGestorErrores());
    }

    @Override
    public void visitar(FuncionHandle f) {
        checkArgumentos(f, 1, null);
        f.setTipo(new Tipo(Tipo.INTEGER));
        Tipos.casting(f.getHandle(), new Tipo(Tipo.FILE), tabla.getGestorErrores());
    }

}
