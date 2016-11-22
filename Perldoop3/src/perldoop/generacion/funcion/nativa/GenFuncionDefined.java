package perldoop.generacion.funcion.nativa;

import perldoop.generacion.util.ColIterator;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Generador de la funcion defined
 *
 * @author César Pomar
 */
public class GenFuncionDefined extends GenFuncionNativa {

    public GenFuncionDefined(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        StringBuilder codigo = new StringBuilder(100);
        ColIterator it = new ColIterator(f.getColeccion());
        codigo.append(it.getComentario());
        codigo.append("(");
        codigo.append(it.next());      
        codigo.append(it.getComentario());
        codigo.append("!= null)");
        f.setCodigoGenerado(codigo);
    }

}
