package perldoop.modelo.arbol.coleccion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.lista.Lista;

/**
 * Clase que representa la reduccion -> coleccion : '{' lista '}'
 *
 * @author César Pomar
 */
public final class ColLlave extends Coleccion{

    private Terminal llaveI;
    private Lista lista;
    private Terminal llaveD;

    /**
     * Único contructor de la clase
     *
     * @param llaveI Llave izquierda
     * @param lista Lista
     * @param llaveD Llave derecha
     */
    public ColLlave(Terminal llaveI, Lista lista, Terminal llaveD) {
        setLlaveI(llaveI);
        setLista(lista);
        setLlaveD(llaveD);
    }

    /**
     * Obtiene el llave izquierda
     *
     * @return Llave izquierda
     */
    public Terminal getLlaveI() {
        return llaveI;
    }

    /**
     * Establece el llave izquierda
     *
     * @param llaveI Llave izquierda
     */
    public void setLlaveI(Terminal llaveI) {
        llaveI.setPadre(this);
        this.llaveI = llaveI;
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
     * obtiene el llave derecha
     *
     * @return Llave derecha
     */
    public Terminal getLlaveD() {
        return llaveD;
    }

    /**
     * Establece el llave derecha
     *
     * @param llaveD Llave derecha
     */
    public void setLlaveD(Terminal llaveD) {
        llaveD.setPadre(this);
        this.llaveD = llaveD;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{llaveI, lista, llaveD};
    }

}
