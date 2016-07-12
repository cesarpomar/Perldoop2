package perldoop.modelo.arbol.varmulti;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.lista.Lista;

/**
 * Clase abtracta que representa todas las reduciones de varMulti
 *
 * @author César Pomar
 */
public abstract class VarMulti extends Simbolo {

    protected Terminal parentesisI;
    protected Lista lista;
    protected Terminal parentesisD;
    
    /**
     * Obtiene el parentesis izquierdo
     *
     * @return Parentesis izquierdo
     */
    public final Terminal getParentesisI() {
        return parentesisI;
    }

    /**
     * Establece el parentesis izquierdo
     *
     * @param parentesisI Parentesis izquierdo
     */
    public final void setParentesisI(Terminal parentesisI) {
        parentesisI.setPadre(this);
        this.parentesisI = parentesisI;
    }

    /**
     * Obtiene la lista
     *
     * @return Lista
     */
    public final Lista getLista() {
        return lista;
    }

    /**
     * Establece la lista
     *
     * @param lista Lista
     */
    public final void setLista(Lista lista) {
        lista.setPadre(this);
        this.lista = lista;
    }

    /**
     * obtiene el parentesis derecho
     *
     * @return Parentesis derecho
     */
    public final Terminal getParentesisD() {
        return parentesisD;
    }

    /**
     * Establece el parentesis derecho
     *
     * @param parentesisD Parentesis derecho
     */
    public final void setParentesisD(Terminal parentesisD) {
        parentesisD.setPadre(this);
        this.parentesisD = parentesisD;
    }

}
