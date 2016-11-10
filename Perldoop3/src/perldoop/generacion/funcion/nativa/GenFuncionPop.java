package perldoop.generacion.funcion.nativa;


import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Generador de la funcion pop
 *
 * @author César Pomar
 */
public class GenFuncionPop extends GenFuncionNativa {

    public GenFuncionPop(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

}
