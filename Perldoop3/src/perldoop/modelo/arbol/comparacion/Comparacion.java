package perldoop.modelo.arbol.comparacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;

/**
 * Clase abtracta que representa todas las reduciones de comparacion
 *
 * @author César Pomar
 */
public abstract class Comparacion extends Simbolo {

    protected Terminal operador;

    /**
     * Único contructor de la clase
     *
     * @param operador Operador
     */
    public Comparacion(Terminal operador) {
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
