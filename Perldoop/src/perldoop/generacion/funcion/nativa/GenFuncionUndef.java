package perldoop.generacion.funcion.nativa;

import perldoop.generacion.util.ColIterator;
import perldoop.modelo.arbol.SimboloAux;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.util.Buscar;

/**
 * Generador de la funcion undef
 *
 * @author César Pomar
 */
public class GenFuncionUndef extends GenFuncionNativa {

    public GenFuncionUndef(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        StringBuilder codigo = new StringBuilder(100);
        if (!Buscar.getExpresiones(f.getColeccion()).isEmpty()) {
            ColIterator it = new ColIterator(f.getColeccion());
            codigo.append(it.getComentario());
            Expresion exp = it.next();
            codigo.append(updateVariable(exp, exp, new SimboloAux(exp.getTipo(), new StringBuilder("null"))));
            codigo.append(it.getComentario());
        } else {
            codigo.append("null");
        }
        f.setCodigoGenerado(codigo);
    }

}
