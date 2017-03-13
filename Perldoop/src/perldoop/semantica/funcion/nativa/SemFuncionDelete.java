package perldoop.semantica.funcion.nativa;

import java.util.List;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.acceso.AccesoCol;
import perldoop.modelo.arbol.acceso.AccesoColRef;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.semantica.TablaSemantica;
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
            tabla.getGestorErrores().error(Errores.DELETE_NO_ACCESO, Buscar.tokenInicio(origen));
        }
        f.setTipo(origen.getTipo());
    }

}
