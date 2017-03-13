package perldoop.semantica.funcion.nativa;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Semantica función undef
 *
 * @author César Pomar
 */
public final class SemFuncionUndef extends SemFuncionNativa {

    public SemFuncionUndef(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        checkArgumentos(f, 0, 1);
        if(!f.getColeccion().getLista().getExpresiones().isEmpty()){
            checkVariable(f.getColeccion().getLista().getExpresiones().get(0), null);
        }
        Simbolo uso = Buscar.getUso((Expresion) f.getPadre());
        if(uso instanceof Igual && ((Igual) uso).getIzquierda().getTipo()!=null){
            f.setTipo(((Igual) uso).getIzquierda().getTipo());
        }else{
            f.setTipo(new Tipo(Tipo.STRING));
        }
    }

}
