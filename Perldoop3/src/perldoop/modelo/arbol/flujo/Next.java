package perldoop.modelo.arbol.flujo;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * /**
 * Clase que representa la reduccion -&gt; flujo : NEXT
 *
 * @author César Pomar
 */
public final class Next extends Flujo {

    /**
     * Único contructor de la clase
     *
     * @param id Id
     */
    public Next(Terminal id) {
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
