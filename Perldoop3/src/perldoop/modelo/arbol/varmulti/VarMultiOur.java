package perldoop.modelo.arbol.varmulti;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.lista.Lista;

/**
 * Clase que representa la reduccion -> varMulti : OUR '(' lista ')'
 *
 * @author César Pomar
 */
public final class VarMultiOur extends VarMulti {

    private Terminal our;

    /**
     * Único contructor de la clase
     *
     * @param our Our
     * @param parentesisI Parentesis izquierdo
     * @param lista Lista
     * @param parentesisD Parentesis derecho
     */
    public VarMultiOur(Terminal our,Terminal parentesisI, Lista lista, Terminal parentesisD) {
        setOur(our);
        setParentesisI(parentesisI);
        setLista(lista);
        setParentesisD(parentesisD);
    }

    /**
     * Obtiene el our
     * @return Our
     */
    public Terminal getOur() {
        return our;
    }

    /**
     * Establece el our
     * @param our Our
     */
    public void setOur(Terminal our) {
        our.setPadre(this);
        this.our = our;
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
