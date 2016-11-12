package perldoop.generacion.funcion.nativa;

import perldoop.generacion.util.Casting;
import perldoop.generacion.util.ColIterator;
import perldoop.modelo.arbol.cadena.CadenaQR;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.util.Buscar;

/**
 * Generador de la funcion chop
 *
 * @author César Pomar
 */
public class GenFuncionSplit extends GenFuncionNativa {

    public GenFuncionSplit(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Perl.").append(f.getIdentificador().getValor());
        //Version alternativa para expresion regular compleja
        if (Buscar.getExpresiones(f.getColeccion()).get(0).getValor() instanceof CadenaQR) {
            codigo.append("2");
        }
        codigo.append(f.getIdentificador().getComentario()).append("(");
        ColIterator it = new ColIterator(f.getColeccion());
        codigo.append(it.getComentario());
        //Regex
        codigo.append(Casting.toString(it.next()));
        codigo.append(it.getComentario());
        //Cadena
        codigo.append(",");
        codigo.append(Casting.toString(it.next()));
        codigo.append(it.getComentario());
        //Limite
        if (it.hasNext()) {
            codigo.append(",");
            codigo.append(Casting.toInteger(it.next()));
            codigo.append(it.getComentario());
        }
        codigo.append(")");
        f.setCodigoGenerado(codigo);
    }

}
