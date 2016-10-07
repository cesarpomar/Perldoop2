package perldoop.modelo.arbol.condicional;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt;<br> condicional : 
 *
 * @author César Pomar
 */
public final class CondicionalNada extends Condicional {

    /**
     * Único contructor de la clase
     */
    public CondicionalNada() {
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
