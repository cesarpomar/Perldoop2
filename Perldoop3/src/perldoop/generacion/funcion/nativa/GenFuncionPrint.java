package perldoop.generacion.funcion.nativa;

import perldoop.generacion.util.ColIterator;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.arbol.funcion.FuncionBloque;
import perldoop.modelo.arbol.funcion.FuncionHandle;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.util.Buscar;

/**
 * Generador de la funcion print
 *
 * @author César Pomar
 */
public class GenFuncionPrint extends GenFuncionNativa {

    public GenFuncionPrint(TablaGenerador tabla) {
        super(tabla);
    }

    /**
     * Construye los argumentos de una funcion print
     *
     * @param col Coleccion de argumentos
     * @param codigo Codigo para la escritura
     * @return Argumento codigo
     */
    private StringBuilder args(Coleccion col, StringBuilder codigo) {
        if (!Buscar.getExpresiones(col).stream().anyMatch(i -> i.getTipo().isColeccion())) {
            ColIterator it = new ColIterator(col);
            codigo.append(it.getComentario());
            while (it.hasNext()) {
                codigo.append(it.next());
                if (it.hasNext()) {
                    codigo.append(",");
                }
                codigo.append(it.getComentario());
            }
        }else{
            codigo.append(col);
        }
        return codigo;
    }

    @Override
    public void visitar(FuncionBasica f) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Perl.").append(f.getIdentificador());
        codigo.append("(");
        args(f.getColeccion(), codigo).append(")");
        f.setCodigoGenerado(codigo);
    }

    @Override
    public void visitar(FuncionBloque f) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(f.getIdentificador()).append("(");
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
        codigo.append(f.getIdentificador()).append("(");
        codigo.append(f.getHandle());
        if (!f.getColeccion().getLista().getExpresiones().isEmpty()) {
            codigo.append(",");
        }
        args(f.getColeccion(), codigo).append(")");
        f.setCodigoGenerado(codigo);
    }

}
