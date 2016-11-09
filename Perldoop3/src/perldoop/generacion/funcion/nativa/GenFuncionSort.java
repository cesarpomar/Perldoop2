package perldoop.generacion.funcion.nativa;

import perldoop.generacion.util.Casting;
import perldoop.generacion.util.ColIterator;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.Funcion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.arbol.funcion.FuncionBloque;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Generador de la funcion sort
 *
 * @author César Pomar
 */
public class GenFuncionSort extends GenFuncionNativa {

    public GenFuncionSort(TablaGenerador tabla) {
        super(tabla);
    }

    /**
     * Genera la funcion sort
     *
     * @param f Funcion
     * @param comparator Comparador
     */
    public void genSort(Funcion f, StringBuilder comparator) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Perl.").append(f.getIdentificador()).append("(");
        if (Buscar.getExpresiones(f.getColeccion()).size() > 1) {
            codigo.append(f.getColeccion());
            codigo.append(comparator);
            codigo.append(")");
        } else {
            ColIterator it = new ColIterator(f.getColeccion());
            codigo.append(it.getComentario());
            Expresion exp = it.next();
            Tipo t = exp.getTipo().getSubtipo(0);
            if (t.isArrayOrList()) {
                codigo.append(exp);
            } else if (t.isMap()) {
                codigo.append(Casting.casting(exp, t.getSubtipo(1).add(0, Tipo.ARRAY)));
            } else {
                codigo.append(Tipos.inicializacion(t.add(0, Tipo.ARRAY))).append("{").append(exp).append("}");
            }
            codigo.append(comparator);
            codigo.append(")");
            codigo.append(it.getComentario());
        }
        f.setCodigoGenerado(codigo);
    }

    @Override
    public void visitar(FuncionBasica f) {
        genSort(f, new StringBuilder());
    }

    @Override
    public void visitar(FuncionBloque f) {
        StringBuilder comparador = new StringBuilder(100);
        comparador.append(",").append(f.getLlaveI().getComentario());
        comparador.append("(a,b)->");        
        comparador.append(Casting.toInteger(f.getExpresion()));
        if (!f.getColeccion().getLista().getSeparadores().isEmpty()) {
            comparador.append(f.getColeccion().getLista().getSeparadores().get(0).getComentario());
        }
        comparador.append(f.getLlaveD().getComentario());
        genSort(f, comparador);
    }

}
