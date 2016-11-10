package perldoop.semantica.funcion.nativa;

import java.util.List;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.coleccion.SemColeccion;
import perldoop.util.Buscar;

/**
 * Semantica función pop
 *
 * @author César Pomar
 */
public final class SemFuncionPop extends SemFuncionNativa {

    public SemFuncionPop(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        checkArgumentos(f, 1, 1);
        List<Expresion> lista = Buscar.getExpresiones(f.getColeccion());
        Expresion exp = lista.get(0);
        checkVariable(exp, null);
        //Comprobar que la variable es un array o lista
        if (!exp.getTipo().isArrayOrList()) {
            errorVariableTipo(lista.get(0), new Tipo(Tipo.ARRAY), new Tipo(Tipo.LIST));
        }
        Tipo t = lista.get(0).getTipo().getSubtipo(1);
        if(t.isColeccion()){
            t.add(0, Tipo.REF);
        }
        f.setTipo(t);
    }

}
