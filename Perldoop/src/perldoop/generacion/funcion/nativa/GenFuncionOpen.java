package perldoop.generacion.funcion.nativa;

import perldoop.generacion.util.Casting;
import perldoop.generacion.util.ColIterator;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.SimboloAux;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Generador de la funcion open
 *
 * @author César Pomar
 */
public class GenFuncionOpen extends GenFuncionNativa {

    public GenFuncionOpen(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Perl.").append(f.getIdentificador()).append("(");
        ColIterator it = new ColIterator(f.getColeccion());
        codigo.append(it.getComentario());
        //Inicializacion del fichero
        Expresion exp = it.next();
        Simbolo escritura = new SimboloAux(exp);
        genVariable(exp, null, escritura);
        codigo.append(updateVariable(exp, escritura, new SimboloAux(exp.getTipo(), new StringBuilder("new PerlFile(),"))));
        //Agumento 2
        codigo.append(it.getComentario());
        codigo.append(Casting.toString(it.next()));
        //Si hay 3 argumento
        if (it.hasNext()) {
            codigo.append(",");
            codigo.append(it.getComentario());
            codigo.append(Casting.toString(it.next()));
        }
        codigo.append(")");
        f.setCodigoGenerado(codigo);
    }

}
