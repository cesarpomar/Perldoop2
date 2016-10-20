package perldoop.modelo.arbol.handle;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; handle : STDERR
 *
 * @author César Pomar
 */
public final class HandleErr extends Handle {

    /**
     * Constructor unico de la clase
     *
     * @param std Salida
     */
    public HandleErr(Terminal std) {
        super(std);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{std};
    }

}
