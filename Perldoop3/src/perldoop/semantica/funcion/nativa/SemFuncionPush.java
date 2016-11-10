package perldoop.semantica.funcion.nativa;

import java.util.List;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.coleccion.SemColeccion;
import perldoop.util.Buscar;

/**
 * Semantica función push
 *
 * @author César Pomar
 */
public final class SemFuncionPush extends SemFuncionNativa {

    public SemFuncionPush(TablaSemantica tabla) {
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
        SemColeccion.comprobarElems(exp.getTipo(), lista.subList(1, lista.size()), tabla);
        f.setTipo(new Tipo(Tipo.INTEGER));
    }

}
