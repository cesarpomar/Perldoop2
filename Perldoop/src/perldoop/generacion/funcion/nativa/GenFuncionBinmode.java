package perldoop.generacion.funcion.nativa;

import perldoop.generacion.util.Casting;
import perldoop.generacion.util.ColIterator;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Generador de la funcion binmode
 *
 * @author César Pomar
 */
public class GenFuncionBinmode extends GenFuncionNativa {

    public GenFuncionBinmode(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Perl.").append(f.getIdentificador()).append("(");
        ColIterator it = new ColIterator(f.getColeccion());
        codigo.append(it.getComentario());
        //Fichero
        codigo.append(it.next());
        codigo.append(it.getComentario());
        //Modo
        codigo.append(Casting.toString(it.next()));
        codigo.append(it.getComentario());
        codigo.append(")");
        f.setCodigoGenerado(codigo);
    }

}
