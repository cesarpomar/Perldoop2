package perldoop.modelo.arbol.coleccion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.lista.Lista;

/**
 * Clase que representa la reduccion -> coleccion : MY '(' lista ')'
 *
 * @author César Pomar
 */
public final class ColMy extends Coleccion {

    private Terminal my;
    private Terminal parentesisI;
    private Lista lista;
    private Terminal parentesisD;

    /**
     * Único contructor de la clase
     *
     * @param my My
     * @param parentesisI Parentesis izquierdo
     * @param lista Lista
     * @param parentesisD Parentesis derecho
     */
    public ColMy(Terminal my, Terminal parentesisI, Lista lista, Terminal parentesisD) {
        setMy(my);
        setParentesisI(parentesisI);
        setLista(lista);
        setParentesisD(parentesisD);
    }

    /**
     * Obtiene el my
     * @return My
     */
    public Terminal getMy() {
        return my;
    }

    /**
     * Establece el my
     * @param my My
     */
    public void setMy(Terminal my) {
        my.setPadre(this);
        this.my = my;
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
