package perldoop.generacion.funcion.nativa;

import java.util.Iterator;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.arbol.funcion.FuncionBloque;
import perldoop.modelo.arbol.funcion.FuncionHandle;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Generador de la funcion print
 *
 * @author César Pomar
 */
public class GenFuncionPrint extends GenFuncionNativa {

    public GenFuncionPrint(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Perl.");
        codigo.append(f.getIdentificador());
        codigo.append("(");
        Iterator<Expresion> it = f.getColeccion().getLista().getExpresiones().iterator();
        while (it.hasNext()) {
            codigo.append(it.next());
            if (it.hasNext()) {
                codigo.append(",");
            }
        }
        codigo.append(")");
        f.setCodigoGenerado(codigo);
    }

    @Override
    public void visitar(FuncionBloque f) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(f.getIdentificador());
        codigo.append("(");
        codigo.append(f.getLista().getExpresiones().get(0));
        Iterator<Expresion> it = f.getColeccion().getLista().getExpresiones().iterator();
        if (it.hasNext()) {
            codigo.append(",");
        }
        while (it.hasNext()) {
            codigo.append(it.next());
            if (it.hasNext()) {
                codigo.append(",");
            }
        }
        codigo.append(")");
        f.setCodigoGenerado(codigo);
    }

    @Override
    public void visitar(FuncionHandle f) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(f.getIdentificador());
        codigo.append("(");
        codigo.append(f.getHandle());
        Iterator<Expresion> it = f.getColeccion().getLista().getExpresiones().iterator();
        if (it.hasNext()) {
            codigo.append(",");
        }
        while (it.hasNext()) {
            codigo.append(it.next());
            if (it.hasNext()) {
                codigo.append(",");
            }
        }
        codigo.append(")");
        f.setCodigoGenerado(codigo);
    }

}
