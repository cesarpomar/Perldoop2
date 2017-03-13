package perldoop.semantica.funcion.nativa;

import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.arbol.funcion.FuncionBloque;
import perldoop.modelo.arbol.funcion.FuncionHandle;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.util.Tipos;

/**
 * Semantica función print
 *
 * @author César Pomar
 */
public final class SemFuncionPrint extends SemFuncionNativa {

    public SemFuncionPrint(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        f.setTipo(new Tipo(Tipo.INTEGER));
    }

    @Override
    public void visitar(FuncionBloque f) {
        f.setTipo(new Tipo(Tipo.INTEGER));
        Tipos.casting(f.getExpresion(), new Tipo(Tipo.FILE), tabla.getGestorErrores());
    }

    @Override
    public void visitar(FuncionHandle f) {
        f.setTipo(new Tipo(Tipo.INTEGER));
        Tipos.casting(f.getHandle(), new Tipo(Tipo.FILE), tabla.getGestorErrores());
    }

}
