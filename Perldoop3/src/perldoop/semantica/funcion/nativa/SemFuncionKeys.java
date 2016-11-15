package perldoop.semantica.funcion.nativa;

import java.util.List;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Semantica función keys
 *
 * @author César Pomar
 */
public final class SemFuncionKeys extends SemFuncionNativa {

    public SemFuncionKeys(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        checkArgumentos(f, 1, 1);
        List<Expresion> lista = Buscar.getExpresiones(f.getColeccion());
        Expresion exp = lista.get(0);
        checkVariable(exp, null);
        //Comprobar que la variable es mapa
        Expresion var = Buscar.getExpresion(exp);
        if (var.getTipo().isMap()) {
            errorVariableTipo(exp, new Tipo(Tipo.MAP));
        }
        f.setTipo(new Tipo(Tipo.LIST,Tipo.STRING));
    }

}
