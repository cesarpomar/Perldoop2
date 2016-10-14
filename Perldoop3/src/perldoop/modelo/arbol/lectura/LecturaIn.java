package perldoop.modelo.arbol.lectura;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; lectura : '-&lt' STDIN '-&gt'
 *
 * @author César Pomar
 */
public final class LecturaIn extends Lectura {

    private Terminal stdin;

    /**
     * Constructor unico de la clase
     *
     * @param menor Menor
     * @param stdin STDIN
     * @param mayor Mayor
     */
    public LecturaIn(Terminal menor, Terminal stdin, Terminal mayor) {
        super(menor, mayor);
        setStdin(stdin);
    }

    /**
     * Obtiene el STDIN
     *
     * @return STDIN
     */
    public Terminal getStdin() {
        return stdin;
    }

    /**
     * Establece el STDIN
     *
     * @param stdin STDIN
     */
    public void setStdin(Terminal stdin) {
        stdin.setPadre(this);
        this.stdin = stdin;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{menor, stdin, mayor};
    }

}
