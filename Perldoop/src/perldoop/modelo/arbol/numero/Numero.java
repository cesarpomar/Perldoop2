package perldoop.modelo.arbol.numero;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;

/**
 * Clase abtracta que representa todas las reduciones numericas
 *
 * @author César Pomar
 */
public abstract class Numero extends Simbolo {

    protected Terminal numero;

    /**
     * Unico contructor de la clase
     *
     * @param numero Numero
     */
    public Numero(Terminal numero) {
        setNumero(numero);
    }

    /**
     * Obtiene el numero
     *
     * @return Numero
     */
    public final Terminal getNumero() {
        return numero;
    }

    /**
     * Establece el numero
     *
     * @param numero Numero
     */
    public final void setNumero(Terminal numero) {
        numero.setPadre(this);
        this.numero = numero;
    }

}
