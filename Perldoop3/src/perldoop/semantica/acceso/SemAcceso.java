package perldoop.semantica.acceso;

import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.acceso.*;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.util.Tipos;
import perldoop.util.Buscar;

/**
 * Clase para la semantica de acceso
 *
 * @author César Pomar
 */
public class SemAcceso {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemAcceso(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(AccesoCol s) {
        Tipo t = s.getExpresion().getTipo();
        Tipo st = t.getSubtipo(1);
        if (t.isColeccion()) {
            Simbolo uso = Buscar.getPadre(s, 1);
            List<Expresion> exps = s.getColeccion().getLista().getExpresiones();
            if (exps.isEmpty()) {
                //Error acceso vacio
            }
            if ((uso instanceof AccesoCol) || (uso instanceof AccesoColRef)) {
                if (exps.size() == 1 && !exps.get(0).getTipo().isColeccion()) {
                    s.setTipo(new Tipo(st));
                } else if (t.isArrayOrList()) {
                    s.setTipo(new Tipo(t));
                } else {
                    s.setTipo(t.getSubtipo(1).add(0, Tipo.LIST));
                }
            } else if (exps.size() == 1) {
                s.setTipo(new Tipo(st));
                if (st.isColeccion()) {
                    st.add(0, Tipo.REF);
                }
            } else {
                s.setTipo(new Tipo(t));
            }
        } else {
            //error acceso
        }
    }

    public void visitar(AccesoColRef s) {
    }

    public void visitar(AccesoRefEscalar s) {
    }

    public void visitar(AccesoRefArray s) {
    }

    public void visitar(AccesoRefMap s) {
    }

    public void visitar(AccesoSigil s) {
    }

    public void visitar(AccesoRef s) {
    }

}
