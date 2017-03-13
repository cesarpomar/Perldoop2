package perldoop.modelo.arbol.flujo;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; flujo : LAST
 *
 * @author César Pomar
 */
public final class Last extends Flujo {

    /**
     * Único contructor de la clase
     *
     * @param id Id
     */
    public Last(Terminal id) {
        super(id);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{id};
    }

}
