package perldoop.generacion.funcion.nativa;

import java.util.ArrayList;
import java.util.List;
import perldoop.generacion.coleccion.GenColeccion;
import perldoop.generacion.util.Casting;
import perldoop.generacion.util.ColIterator;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.SimboloAux;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Generador de la funcion push
 *
 * @author César Pomar
 */
public class GenFuncionPush extends GenFuncionNativa {

    public GenFuncionPush(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        StringBuilder codigo = new StringBuilder(100);
        Simbolo escritura = null;
        codigo.append("Perl.").append(f.getIdentificador()).append("(");
        ColIterator it = new ColIterator(f.getColeccion());
        codigo.append(it.getComentario());
        //Separador
        Expresion coleccion = it.next();
        if (coleccion.getTipo().isArray()) {//Actualizar con el retorno
            Simbolo lectura = new SimboloAux(coleccion);
            escritura = new SimboloAux(lectura);
            genVariable(coleccion, lectura, escritura);
            codigo.append(lectura);
        }else{
            codigo.append(coleccion);
        }
        Tipo st = coleccion.getTipo().getSubtipo(1);
        if(st.isColeccion()){
            st.add(0, Tipo.REF);
        }
        codigo.append(",");
        codigo.append(it.getComentario());
        //Metodo simple, dos
        if (Buscar.getExpresiones(f.getColeccion()).size() == 2) {
            Expresion exp = it.next();
            if (!exp.getTipo().isColeccion()) {
                codigo.append(Casting.casting(exp, st));
            } else if (exp.getTipo().isArray() && st.equals(exp.getTipo().getSubtipo(1))) {
                codigo.append("Pd.tList(").append(exp).append(")");
            } else {
                codigo.append(Casting.casting(exp, st.add(0, Tipo.LIST)));
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
            codigo.append(GenColeccion.genArrayList(exps, seps, coleccion.getTipo()));
        }
        if (coleccion.getTipo().isArray()) {//Retornar valor real  
            String varAux = genReturnVar(f, codigo);
            codigo = updateVariable(coleccion, escritura, new SimboloAux(coleccion.getTipo(), codigo));
            genReturn(varAux, codigo);
        }else{
            codigo.append(")");
        }
        f.setCodigoGenerado(codigo);
    }

}
