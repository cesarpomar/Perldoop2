package perldoop.modelo.arbol.std;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; std : STDERR
 *
 * @author César Pomar
 */
public final class StdErr extends Std {

    /**
     * Contructor unico de la clase
     *
     * @param handle Handle
     */
    public StdErr(Terminal handle) {
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