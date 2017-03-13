package perldoop.modelo.arbol.std;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; std : STDOUT
 *
 * @author César Pomar
 */
public final class StdOut extends Std {

    /**
     * Contructor unico de la clase
     *
     * @param handle Handle
     */
    public StdOut(Terminal handle) {
        super(handle);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{handle};
    }

}
