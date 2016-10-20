package perldoop.modelo.arbol.escritura;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; escritura : STDERR
 *
 * @author César Pomar
 */
public final class EscrituraErr extends Escritura {

    /**
     * Contructor unico de la clase
     *
     * @param handle Handle
     */
    public EscrituraErr(Terminal handle) {
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