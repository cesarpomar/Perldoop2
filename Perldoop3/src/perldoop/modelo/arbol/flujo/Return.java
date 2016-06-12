package perldoop.modelo.arbol.flujo;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.lista.Lista;

/**
 * Clase que representa la reduccion -> <br>
 * flujo : RETURN ';'<br>
 * | RETURN lista ';'
 *
 * @author César Pomar
 */
public final class Return extends Flujo {

    private Terminal returnF;
    private Lista lista;
    private Terminal puntoComa;

    /**
     * Único contructor de la clase
     *
     * @param returnF Return
     * @param lista Lista
     * @param puntoComa PuntoComa
     */
    public Return(Terminal returnF, Lista lista, Terminal puntoComa) {
        setReturnF(returnF);
        setLista(lista);
        setPuntoComa(puntoComa);
    }

    /**
     * Obtiene el return
     *
     * @return Return
     */
    public Terminal getReturnF() {
        return returnF;
    }

    /**
     * Establece el return
     *
     * @param returnF Return
     */
    public void setReturnF(Terminal returnF) {
        returnF.setPadre(this);
        this.returnF = returnF;
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
     * @param lista
     */
    public final void setLista(Lista lista) {
        lista.setPadre(this);
        this.lista = lista;
    }

    /**
     * Obtiene el punto y coma ';'
     *
     * @return PuntoComa
     */
    public final Terminal getPuntoComa() {
        return puntoComa;
    }

    /**
     * Establece el punto y coma ';'
     *
     * @param puntoComa PuntoComa
     */
    public final void setPuntoComa(Terminal puntoComa) {
        puntoComa.setPadre(this);
        this.puntoComa = puntoComa;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{returnF};
    }

}
