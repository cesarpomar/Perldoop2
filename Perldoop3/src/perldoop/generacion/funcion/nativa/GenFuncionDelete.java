package perldoop.generacion.funcion.nativa;

import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Generador de la funcion delete
 *
 * @author César Pomar
 */
public class GenFuncionDelete extends GenFuncionNativa {

    public GenFuncionDelete(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        throw new UnsupportedOperationException(f.getIdentificador().getValor() + " Not supported yet.");
    }

}
