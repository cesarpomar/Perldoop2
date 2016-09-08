package perldoop.modelo.arbol.varmulti;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.lista.Lista;

/**
 * Clase que representa la reduccion -&gt; coleccion : VarMulti '(' lista ')'
 *
 * @author César Pomar
 */
public final class VarMultiMy extends VarMulti {

    private Terminal my;

    /**
     * Único contructor de la clase
     *
     * @param my My
     * @param parentesisI Parentesis izquierdo
     * @param lista Lista
     * @param parentesisD Parentesis derecho
     */
    public VarMultiMy(Terminal my, Terminal parentesisI, Lista lista, Terminal parentesisD) {
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
    
    

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{parentesisI, lista, parentesisD};
    }
}
