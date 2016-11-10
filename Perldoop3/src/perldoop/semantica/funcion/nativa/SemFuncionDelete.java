package perldoop.semantica.funcion.nativa;

import java.util.List;
import perldoop.modelo.arbol.acceso.AccesoCol;
import perldoop.modelo.arbol.acceso.AccesoColRef;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.coleccion.SemColeccion;
import perldoop.util.Buscar;

/**
 * Semantica función delete
 *
 * @author César Pomar
 */
public final class SemFuncionDelete extends SemFuncionNativa {

    public SemFuncionDelete(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        checkArgumentos(f, 1, 1);
        List<Expresion> lista = Buscar.getExpresiones(f.getColeccion());
        Expresion exp = lista.get(0);
        checkVariable(exp, null);
        //Comprobar que la variable es un acceso
        Expresion origen = Buscar.getExpresion(exp);
        if (!(origen.getValor() instanceof AccesoCol || origen.getValor() instanceof AccesoColRef)) {
            errorVariableTipo(lista.get(0), new Tipo(Tipo.LIST), new Tipo(Tipo.MAP));
        }
        f.setTipo(origen.getTipo());
    }

}
