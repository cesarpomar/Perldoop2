package perldoop.modelo.arbol.coleccion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.lista.Lista;

/**
 * Clase que representa la reduccion -> coleccion : '[' lista ']'
 *
 * @author César Pomar
 */
public final class ColCorchete extends Coleccion{

    private Terminal corcheteI;
    private Lista lista;
    private Terminal corcheteD;

    /**
     * Único contructor de la clase
     *
     * @param corcheteI Corchete izquierdo
     * @param lista Lista
     * @param corcheteD Corchete derecho
     */
    public ColCorchete(Terminal corcheteI, Lista lista, Terminal corcheteD) {
        setCorcheteI(corcheteI);
        setLista(lista);
        setCorcheteD(corcheteD);
    }

    /**
     * Obtiene el corchete izquierdo
     *
     * @return Corchete  izquierdo
     */
    public Terminal getCorcheteI() {
        return corcheteI;
    }

    /**
     * Establece el corchete izquierdo
     *
     * @param corcheteI Corchete izquierdo
     */
    public void setCorcheteI(Terminal corcheteI) {
        corcheteI.setPadre(this);
        this.corcheteI = corcheteI;
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
     * obtiene el corchete derecho
     *
     * @return Corchete derecho
     */
    public Terminal getCorcheteD() {
        return corcheteD;
    }

    /**
     * Establece el corchete derecho
     *
     * @param corcheteD Corchete derecho
     */
    public void setCorcheteD(Terminal corcheteD) {
        corcheteD.setPadre(this);
        this.corcheteD = corcheteD;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{corcheteI, lista, corcheteD};
    }

}
