package perldoop.modelo.arbol.varmulti;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.lista.Lista;

/**
 * Clase que representa la reduccion -&gt; coleccion : VarMulti '(' lista ')'
 *
 * @author César Pomar
 */
public final class VarMultiMy extends VarMulti {

    /**
     * Único contructor de la clase
     *
     * @param operador Operador
     * @param parentesisI Parentesis izquierdo
     * @param lista Lista
     * @param parentesisD Parentesis derecho
     */
    public VarMultiMy(Terminal operador, Terminal parentesisI, Lista lista, Terminal parentesisD) {
        super(operador, parentesisI, lista, parentesisD);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{operador, parentesisI, lista, parentesisD};
    }
}
