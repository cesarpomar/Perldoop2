package perldoop.semantica.acceso;

import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.acceso.*;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.coleccion.ColLlave;
import perldoop.modelo.arbol.expresion.ExpAcceso;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
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
        if (t.isRef() && s.getExpresion() instanceof ExpAcceso) {
            t = t.getSubtipo(1);
        }
        Tipo st = t.getSubtipo(1);
        if (t.isColeccion()) {
            List<Expresion> lista = s.getColeccion().getLista().getExpresiones();
            if (s.getColeccion() instanceof ColCorchete && !t.isArrayOrList()) {
                //Error de acceso
            } else if (s.getColeccion() instanceof ColLlave && !t.isMap()) {
                //Error de acceso
            } else if (lista.isEmpty()) {
                //Error acceso vacio
            } else if (lista.size() == 1 && !lista.get(0).getTipo().isColeccion()) {
                s.setTipo(st);
                if (st.isColeccion()) {
                    st.add(0, Tipo.REF);
                }
            } else if (t.isArrayOrList()) {
                s.setTipo(new Tipo(t));
            } else {
                s.setTipo(t.getSubtipo(1).add(0, Tipo.LIST));
            }
        } else {
            //error acceso
        }
    }

    public void visitar(AccesoColRef s) {
        Tipo t = s.getExpresion().getTipo();
        if(!t.isRef()){
            //Error no referencia
        }
        

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
