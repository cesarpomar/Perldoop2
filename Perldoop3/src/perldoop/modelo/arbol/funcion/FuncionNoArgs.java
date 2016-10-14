package perldoop.modelo.arbol.funcion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; funcion : ID
 *
 * @author César Pomar
 */
public final class FuncionNoArgs extends Funcion {

    /**
     * Único contructor de la clase
     *
     * @param identificador Identificador
     */
    public FuncionNoArgs(Terminal identificador) {
        super(identificador);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{identificador};
    }
}
