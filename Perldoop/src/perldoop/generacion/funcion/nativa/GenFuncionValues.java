package perldoop.generacion.funcion.nativa;

import perldoop.generacion.util.ColIterator;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Generador de la funcion values
 *
 * @author César Pomar
 */
public class GenFuncionValues extends GenFuncionNativa {

    public GenFuncionValues(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Perl.").append(f.getIdentificador()).append("(");
        ColIterator it = new ColIterator(f.getColeccion());
        codigo.append(it.getComentario()).append(it.next()).append(it.getComentario());
        codigo.append(")");
        f.setCodigoGenerado(codigo);
    }

}
