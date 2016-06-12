package perldoop.modelo.arbol.coleccion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.lista.Lista;

/**
 * Clase que representa la reduccion -> coleccion : '(' lista ')'
 *
 * @author César Pomar
 */
public final class ColParentesis extends Coleccion {

    private Terminal parentesisI;
    private Lista lista;
    private Terminal parentesisD;

    /**
     * Único contructor de la clase
     *
     * @param parentesisI Parentesis izquierdo
     * @param lista Lista
     * @param parentesisD Parentesis derecho
     */
    public ColParentesis(Terminal parentesisI, Lista lista, Terminal parentesisD) {
        setParentesisI(parentesisI);
        setLista(lista);
        setParentesisD(parentesisD);
    }

    /**
     * Obtiene el parentesis izquierdo
     *
     * @return Parentesis izquierdo
     */
    public Terminal getParentesisI() {
        return parentesisI;
    }

    /**
     * Establece el parentesis izquierdo
     *
     * @param parentesisI Parentesis izquierdo
     */
    public void setParentesisI(Terminal parentesisI) {
        parentesisI.setPadre(this);
        this.parentesisI = parentesisI;
    }

    /**
     * Obtiene la lista
     *
     * @return Lista
     */
    public Lista getLista() {
        return lista;
    }

    /**
     * Establece la lista
     *
     * @param lista Lista
     */
    public void setLista(Lista lista) {
        lista.setPadre(this);
        this.lista = lista;
    }

    /**
     * obtiene el parentesis derecho
     *
     * @return Parentesis derecho
     */
    public Terminal getParentesisD() {
        return parentesisD;
    }

    /**
     * Establece el parentesis derecho
     *
     * @param parentesisD Parentesis derecho
     */
    public void setParentesisD(Terminal parentesisD) {
        parentesisD.setPadre(this);
        this.parentesisD = parentesisD;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{parentesisI, lista, parentesisD};
    }

}
