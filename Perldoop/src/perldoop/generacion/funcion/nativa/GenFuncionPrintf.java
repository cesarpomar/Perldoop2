package perldoop.generacion.funcion.nativa;

import java.util.ArrayList;
import java.util.List;
import perldoop.generacion.coleccion.GenColeccion;
import perldoop.generacion.util.Casting;
import perldoop.generacion.util.ColIterator;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.arbol.funcion.FuncionBloque;
import perldoop.modelo.arbol.funcion.FuncionHandle;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Generador de la funcion printf
 *
 * @author César Pomar
 */
public class GenFuncionPrintf extends GenFuncionNativa {

    public GenFuncionPrintf(TablaGenerador tabla) {
        super(tabla);
    }

    /**
     * Construye los argumentos de una funcion tipo printf
     *
     * @param col Coleccion de argumentos
     * @param codigo Codigo para la escritura
     * @return Argumento codigo
     */
    private static StringBuilder args(Coleccion col, StringBuilder codigo) {
        ColIterator it = new ColIterator(col);
        codigo.append(it.getComentario());
        //Cadena con el formato
        codigo.append(Casting.toString(it.next()));
        if (it.hasNext()) {
            codigo.append(",");
        }
        codigo.append(it.getComentario());
        if (!Buscar.getExpresiones(col).stream().anyMatch(i -> i.getTipo().isColeccion())) {
            while (it.hasNext()) {
                codigo.append(it.next());
                if (it.hasNext()) {
                    codigo.append(",");
                }
                codigo.append(it.getComentario());
            }
        } else {
            List<Expresion> exps = new ArrayList<>(10);
            List<Terminal> seps = new ArrayList<>(10);
            while (it.hasNext()) {
                exps.add(it.next());
                Terminal t = new Terminal();
                t.setComentario(it.getComentario().toString());
                seps.add(t);
            }
            codigo.append(GenColeccion.genColeccion(exps, seps, new Tipo(Tipo.ARRAY, Tipo.BOX)));
        }
        return codigo;
    }

    @Override
    public void visitar(FuncionBasica f) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Perl.").append(f.getIdentificador()).append("(");
        args(f.getColeccion(), codigo).append(")");
        f.setCodigoGenerado(codigo);
    }

    @Override
    public void visitar(FuncionBloque f) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Perl.").append(f.getIdentificador()).append("(");
        codigo.append(f.getExpresion());
        if (!f.getColeccion().getLista().getExpresiones().isEmpty()) {
            codigo.append(",");
        }
        args(f.getColeccion(), codigo).append(")");
        f.setCodigoGenerado(codigo);
    }

    @Override
    public void visitar(FuncionHandle f) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Perl.").append(f.getIdentificador()).append("(");
        codigo.append(f.getHandle());
        if (!f.getColeccion().getLista().getExpresiones().isEmpty()) {
            codigo.append(",");
        }
        args(f.getColeccion(), codigo).append(")");
        f.setCodigoGenerado(codigo);
    }

}
