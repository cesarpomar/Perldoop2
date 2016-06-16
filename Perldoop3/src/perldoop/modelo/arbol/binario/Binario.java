package perldoop.modelo.arbol.binario;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;

/**
 * Clase abtracta que representa todas las reduciones de binario
 *
 * @author César Pomar
 */
public abstract class Binario extends Simbolo {

    protected Terminal operador;

    /**
     * Único contructor de la clase
     *
     * @param operador Operador
     */
    public Binario(Terminal operador) {
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
