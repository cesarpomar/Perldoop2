package perldoop.generacion.funcion.nativa;


import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Generador de la funcion push
 *
 * @author César Pomar
 */
public class GenFuncionPush extends GenFuncionNativa {

    public GenFuncionPush(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

}
