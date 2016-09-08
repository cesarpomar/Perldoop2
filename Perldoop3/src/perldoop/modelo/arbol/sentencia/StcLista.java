package perldoop.modelo.arbol.sentencia;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.modificador.Modificador;

/**
 * Clase que representa la reduccion -&gt; sentencia : lista modificador ';'
 *
 * @author César Pomar
 */
public final class StcLista extends Sentencia {

    private Lista lista;
    private Modificador modificador;
    private Terminal puntoComa;

    /**
     * Único contructor de la clase
     *
     * @param lista Lista
     * @param modificador Modificador
     * @param puntoComa PuntoComa
     */
    public StcLista(Lista lista, Modificador modificador, Terminal puntoComa) {
        setLista(lista);
        setModificador(modificador);
        setPuntoComa(puntoComa);
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
     * @param lista Tabla
     */
    public final void setLista(Lista lista) {
        lista.setPadre(this);
        this.lista = lista;
    }

    /**
     * Obtiene el modificador
     *
     * @return Modificador
     */
    public Modificador getModificador() {
        return modificador;
    }

    /**
     * Establece el modificador
     *
     * @param modificador Modificador
     */
    public void setModificador(Modificador modificador) {
        modificador.setPadre(this);
        this.modificador = modificador;
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
        return new Simbolo[]{lista, modificador, puntoComa};
    }
}
