package perldoop.generacion.funcion.nativa;

import perldoop.generacion.util.ColIterator;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Generador de la funcion delete
 *
 * @author César Pomar
 */
public class GenFuncionDelete extends GenFuncionNativa {

    public GenFuncionDelete(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Perl.").append(f.getIdentificador()).append("(");
        ColIterator it = new ColIterator(f.getColeccion());
        codigo.append(it.getComentario()).append(it.next()).append(it.getComentario());
        codigo.append(")");
        if(f.getTipo().isRef() && !isSentencia(f)){
           codigo.insert(0, Tipos.declaracion(f.getTipo()).insert(0, "new ").append("(")).append(")");
        }   
        f.setCodigoGenerado(codigo);
    }

}
