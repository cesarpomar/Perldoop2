package perldoop.modelo.arbol.modificador;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -> modificador :
 *
 * @author César Pomar
 */
public final class ModNada extends Modificador {

    /**
     * Único contructor de la clase
     */
    public ModNada() {
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{};
    }

}
