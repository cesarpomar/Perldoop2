package perldoop.modelo.arbol.lectura;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; lectura : '-&lt' '-&gt'
 *
 * @author César Pomar
 */
public final class LecturaIn extends Lectura {

    /**
     * Constructor unico de la clase
     *
     * @param menor Menor
     * @param mayor Mayor
     */
    public LecturaIn(Terminal menor, Terminal mayor) {
        super(menor, mayor);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{menor, mayor};
    }

}
