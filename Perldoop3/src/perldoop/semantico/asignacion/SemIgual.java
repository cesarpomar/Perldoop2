package perldoop.semantico.asignacion;

import java.util.List;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.expresion.ExpVariable;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 *
 * @author César
 */
public class SemIgual {

    public void visitar(Igual s) {
        /*
        if (s.getIzquierda() instanceof ExpMultiple) {
            if (s.getDerecha() instanceof ExpMultiple) {
                multipleMultiple(s);
            } else {
                multipleUno(s);
            }
        } else if (s.getDerecha() instanceof ExpMultiple) {
            unoMultiple(s);
        } else {
            unoUno(s);
        }*/
    }

    private void unoUno(Igual s) {
        if (!(s.getIzquierda() instanceof ExpVariable)) {
            throw new ExcepcionSemantica();
        } else if (!s.getDerecha().getTipo().compatible(s.getIzquierda().getTipo())) {
            throw new ExcepcionSemantica();
        }
        s.setTipo(s.getIzquierda().getTipo());
    }

    private void multipleMultiple(Igual s) {
        /*
        List<Expresion> izquierdaL = ((ExpMultiple) s.getIzquierda()).getLista().getExpresiones();
        List<Expresion> derechaL = ((ExpMultiple) s.getDerecha()).getLista().getExpresiones();
        if (izquierdaL.size() != derechaL.size()) {
            //Error distinto numero de elementos
        }
        for (int i = 0; i < izquierdaL.size(); i++) {
            Expresion expD = derechaL.get(i);
            Expresion expI = izquierdaL.get(i);
            if (expI instanceof ExpVariable) {
                //Error no variable
            } else if (!expD.getTipo().compatible(expI.getTipo())) {
                //Error tipo
            }

        }*/
    }

    private void unoMultiple(Igual s) {
        /*
        List<Expresion> derechaL = ((ExpMultiple) s.getDerecha()).getLista().getExpresiones();
        if (!(s.getIzquierda() instanceof ExpVariable)) {
            //Error no variable
        }
        for (Expresion exp : derechaL) {
            if (exp.getTipo().compatible(s.getIzquierda().getTipo())) {
                //Error tipo no casteable
            }
        }*/

    }

    private void multipleUno(Igual s) {
        /*
        List<Expresion> izquierdaL = ((ExpMultiple) s.getIzquierda()).getLista().getExpresiones();
        for (Expresion exp : izquierdaL) {
            if (!(exp instanceof ExpVariable)) {
                //Error no variable
            } else if (exp.getTipo().compatible(s.getDerecha().getTipo())) {
                //Error tipo no casteable
            }
        }*/
    }
}
