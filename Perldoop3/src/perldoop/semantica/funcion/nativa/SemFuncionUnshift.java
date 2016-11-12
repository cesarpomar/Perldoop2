package perldoop.semantica.funcion.nativa;

import java.util.List;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Semantica función unshift
 *
 * @author César Pomar
 */
public final class SemFuncionUnshift extends SemFuncionNativa {

    public SemFuncionUnshift(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        checkArgumentos(f, 2, null);
        List<Expresion> lista = Buscar.getExpresiones(f.getColeccion());
        Expresion exp = lista.get(0);
        checkVariable(exp, null);
        //Comprobar que la variable es un array o lista
        if (!exp.getTipo().isArrayOrList()) {
            errorVariableTipo(lista.get(0), new Tipo(Tipo.ARRAY), new Tipo(Tipo.LIST));
        }
        //Comprobar que todos los elementos son compatibles con la coleccion
        checkTipoArgs(exp.getTipo(), lista.subList(1, lista.size()));
        f.setTipo(new Tipo(Tipo.INTEGER));
    }

}
