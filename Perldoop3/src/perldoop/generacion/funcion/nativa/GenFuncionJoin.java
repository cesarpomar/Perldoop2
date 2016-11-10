package perldoop.generacion.funcion.nativa;


import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Generador de la funcion join
 *
 * @author César Pomar
 */
public class GenFuncionJoin extends GenFuncionNativa {

    public GenFuncionJoin(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

}
