package perldoop.generacion.funcion.nativa;

import perldoop.generacion.util.Casting;
import perldoop.generacion.util.ColIterator;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.SimboloAux;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Tipo;

/**
 * Generador de la funcion print
 *
 * @author César Pomar
 */
public class GenFuncionChomp extends GenFuncionNativa {

    public GenFuncionChomp(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        StringBuilder codigo = new StringBuilder(100);
        String aux;
        codigo.append("Perl.").append(f.getIdentificador()).append("(");
        ColIterator it = new ColIterator(f.getColeccion());
        codigo.append(it.getComentario());
        Expresion exp = it.next();
        Simbolo lectura = new SimboloAux(exp);
        Simbolo escritura = new SimboloAux(lectura);
        genVariable(exp, lectura, escritura);
        codigo.append(Casting.toString(lectura));
        codigo.append(it.getComentario());
        aux = genReturnVar(f, codigo);
        codigo = updateVariable(exp, escritura, new SimboloAux(new Tipo(Tipo.STRING),codigo));
        genReturn(aux, codigo);
        f.setCodigoGenerado(codigo);
        
    }

}
