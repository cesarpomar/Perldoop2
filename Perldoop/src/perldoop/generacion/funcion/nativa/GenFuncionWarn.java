package perldoop.generacion.funcion.nativa;

import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Generador de la funcion warn
 *
 * @author César Pomar
 */
public class GenFuncionWarn extends GenFuncionNativa {

    public GenFuncionWarn(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Perl.").append(f.getIdentificador());
        codigo.append("(");
        GenFuncionPrint.args(f.getColeccion(), codigo).append(")");
        f.setCodigoGenerado(codigo);
    }

}
