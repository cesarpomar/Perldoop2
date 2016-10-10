package perldoop.modelo.arbol.coleccion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.lista.Lista;

/**
 * Clase que representa la reduccion -&gt; coleccion : '(' lista ')' | '(' ')'
 *
 * @author César Pomar
 */
public final class ColParentesis extends Coleccion {

    private Terminal parentesisI;
    private Terminal parentesisD;
    private boolean virtual;

    /**
     * Constructor completo de una colección
     *
     * @param parentesisI Parentesis izquierdo
     * @param lista Lista
     * @param parentesisD Parentesis derecho
     */
    public ColParentesis(Terminal parentesisI, Lista lista, Terminal parentesisD) {
        super(lista);
        setParentesisI(parentesisI);
        setParentesisD(parentesisD);
        virtual = false;
    }

    /**
     * Constructor simplificado para colecciones virtuales
     *
     * @param lista Lista
     */
    public ColParentesis(Lista lista) {
        super(lista);
        virtual = true;
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

    /**
     * Comprueba si la coleccion es virtual, las colecciones virtuales no tienen terminales parentesis
     *
     * @return Es virtual
     */
    public boolean isVirtual() {
        return virtual;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        if (parentesisI != null) {
            return new Simbolo[]{parentesisI, lista, parentesisD};
        }
        return new Simbolo[]{lista};
    }

}
