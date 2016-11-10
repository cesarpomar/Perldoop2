package perldoop.generacion.funcion.nativa;


import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Generador de la funcion unshift
 *
 * @author César Pomar
 */
public class GenFuncionUnshift extends GenFuncionNativa {

    public GenFuncionUnshift(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

}
