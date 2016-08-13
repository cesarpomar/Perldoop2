package perldoop.modelo.arbol.funcion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;

/**
 * Clase abtracta que representa todas las reduciones de funcion
 *
 * @author César Pomar
 */
public abstract class Funcion extends Simbolo {

    protected Terminal identificador;

    /**
     * Único contructor de la clase
     *
     * @param identificador Identificador
     */
    public Funcion(Terminal identificador) {
        setIdentificador(identificador);
    }

    /**
     * Obtiene el identificador
     *
     * @return Identificador
     */
    public final Terminal getIdentificador() {
        return identificador;
    }

    /**
     * Establece el identificador
     *
     * @param identificador Identificador
     */
    public final void setIdentificador(Terminal identificador) {
        identificador.setPadre(this);
        this.identificador = identificador;
    }

}
