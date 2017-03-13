package perldoop.generacion.funcion.nativa;

import java.util.ArrayList;
import java.util.List;
import perldoop.generacion.coleccion.GenColeccion;
import perldoop.generacion.util.Casting;
import perldoop.generacion.util.ColIterator;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Generador de la funcion join
 *
 * @author César Pomar
 */
public class GenFuncionJoin extends GenFuncionNativa {

    public GenFuncionJoin(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Perl.").append(f.getIdentificador()).append("(");
        ColIterator it = new ColIterator(f.getColeccion());
        codigo.append(it.getComentario());
        //Separador
        codigo.append(Casting.toString(it.next()));
        codigo.append(",");
        codigo.append(it.getComentario());
        //Metodo simple, dos
        if (Buscar.getExpresiones(f.getColeccion()).size() == 2) {
            Expresion exp = it.next();
            if(exp.getTipo().isArray() || !exp.getTipo().isColeccion()){
                codigo.append("Pd.tList(").append(exp).append(")");
            }else{
                codigo.append(Casting.casting(exp, new Tipo(Tipo.LIST,Tipo.STRING)));
            }
            codigo.append(it.getComentario());
        } else {
            List<Expresion> exps = new ArrayList<>(100);
            List<Terminal> seps = new ArrayList<>(100);
            while (it.hasNext()) {
                exps.add(it.next());
                Terminal ter = new Terminal();
                ter.setComentario(it.getComentario().toString());
                seps.add(ter);
            }
            codigo.append(GenColeccion.genColeccion(exps, seps, new Tipo(Tipo.LIST, Tipo.STRING)));
        }
        codigo.append(")");
        f.setCodigoGenerado(codigo);
    }

}
