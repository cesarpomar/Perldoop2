package perldoop.modelo.arbol.elsif;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -> elsif : 
 *
 * @author César Pomar
 */
public class ElsIfNada extends ElsIf {

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{};
    }

}
