package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt;<br> condicional :
 *
 * @author César Pomar
 */
public final class SubBloqueVacio extends SubBloque {

    /**
     * Único contructor de la clase
     */
    public SubBloqueVacio() {
        super();
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
