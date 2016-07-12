package perldoop.modelo.arbol.coleccion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.lista.Lista;

/**
 * Clase abtracta que representa todas las reduciones de coleccion
 *
 * @author César Pomar
 */
public abstract class Coleccion extends Simbolo {

    protected Lista lista;

    /**
     * Unico constructor de la clase
     *
     * @param lista Lista
     */
    public Coleccion(Lista lista) {
        setLista(lista);
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

}
