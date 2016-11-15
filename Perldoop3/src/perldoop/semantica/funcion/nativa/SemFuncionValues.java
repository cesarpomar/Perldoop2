package perldoop.semantica.funcion.nativa;

import java.util.List;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Semantica función values
 *
 * @author César Pomar
 */
public final class SemFuncionValues extends SemFuncionNativa {

    public SemFuncionValues(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        checkArgumentos(f, 1, 1);
        List<Expresion> lista = Buscar.getExpresiones(f.getColeccion());
        Expresion exp = lista.get(0);
        checkVariable(exp, null);
        //Comprobar que la variable es un mapa
        Expresion var = Buscar.getExpresion(exp);
        if (var.getTipo().isMap()) {
            errorVariableTipo(exp, new Tipo(Tipo.MAP));
        }
        f.setTipo(var.getTipo().getSubtipo(1).add(0, Tipo.LIST));
    }

}
