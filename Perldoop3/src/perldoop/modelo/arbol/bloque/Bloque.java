package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.contexto.Contexto;

/**
 * Clase abtracta que representa todas las reduciones de bloque
 *
 * @author César Pomar
 */
public abstract class Bloque extends Simbolo {

    protected Contexto contexto;

    /**
     * Único contructor de la clase
     *
     * @param contexto Contexto del bloque
     */
    public Bloque(Contexto contexto) {
        setContexto(contexto);
    }

    /**
     * Obtiene el contexto del bloque
     *
     * @return Contexto del bloque
     */
    public final Contexto getContexto() {
        return contexto;
    }

    /**
     * Establece el contexto del bloque
     *
     * @param contexto Contexto del bloque
     */
    public final void setContexto(Contexto contexto) {
        contexto.setPadre(this);
        this.contexto = contexto;
    }

}
