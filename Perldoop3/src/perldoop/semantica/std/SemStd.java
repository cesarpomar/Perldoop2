package perldoop.semantica.std;

import perldoop.modelo.arbol.std.StdErr;
import perldoop.modelo.arbol.std.StdIn;
import perldoop.modelo.arbol.std.StdOut;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;

/**
 * Clase para la semantica de std
 *
 * @author César Pomar
 */
public class SemStd {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemStd(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(StdErr s) {
        s.setTipo(new Tipo(Tipo.FILE));
    }

    public void visitar(StdOut s) {
        s.setTipo(new Tipo(Tipo.FILE));
    }

    public void visitar(StdIn s) {
        s.setTipo(new Tipo(Tipo.FILE));
    }

}
