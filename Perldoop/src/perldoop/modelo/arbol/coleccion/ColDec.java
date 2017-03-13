package perldoop.modelo.arbol.coleccion;

import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.lista.Lista;

/**
 * Clase que representa la reduccion -&gt; coleccion : my/our '(' lista ')'
 *
 * @author César Pomar
 */
public abstract class ColDec extends ColParentesis {

    protected Terminal operador;

    /**
     * Constructor completo de una colección
     *
     * @param operador Operador
     * @param parentesisI Parentesis izquierdo
     * @param lista Lista
     * @param parentesisD Parentesis derecho
     */
    public ColDec(Terminal operador, Terminal parentesisI, Lista lista, Terminal parentesisD) {
        super(parentesisI, lista, parentesisD);
        setOperador(operador);
    }

    /**
     * Obtiene el operador
     *
     * @return Operador
     */
    public final Terminal getOperador() {
        return operador;
    }

    /**
     * Establece el operador
     *
     * @param operador Operador
     */
    public final void setOperador(Terminal operador) {
        operador.setPadre(this);
        this.operador = operador;
    }


}
