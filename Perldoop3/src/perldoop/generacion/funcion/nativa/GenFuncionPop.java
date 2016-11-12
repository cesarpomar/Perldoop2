package perldoop.generacion.funcion.nativa;

import perldoop.generacion.util.ColIterator;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.SimboloAux;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Generador de la funcion pop
 *
 * @author César Pomar
 */
public class GenFuncionPop extends GenFuncionNativa {

    public GenFuncionPop(TablaGenerador tabla) {
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
        if (exp.getTipo().isArray()) {
            Simbolo lectura = new SimboloAux(exp);
            Simbolo escritura = new SimboloAux(lectura);
            genVariable(exp, lectura, escritura);
            codigo.append(lectura);
            codigo.append(it.getComentario());
            aux = genReturnVar(f, codigo);
            codigo = updateVariable(exp, escritura, new SimboloAux(exp.getTipo(), codigo));
            genReturn(aux, codigo);
        } else {
            codigo.append(exp);
            codigo.append(")");
            codigo.append(it.getComentario());
        }
        if(f.getTipo().isRef() && !isSentencia(f)){
           codigo.insert(0, Tipos.declaracion(f.getTipo()).insert(0, "new ").append("(")).append(")");
        }
        f.setCodigoGenerado(codigo);
    }

}
