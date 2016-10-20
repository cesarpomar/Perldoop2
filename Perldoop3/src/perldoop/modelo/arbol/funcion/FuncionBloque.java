package perldoop.modelo.arbol.funcion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.paquete.Paquetes;

/**
 * Clase que representa la reduccion -&gt; funcion : Paquete ID expresion
 *
 * @author César Pomar
 */
public final class FuncionBloque extends Funcion {

    protected Terminal llaveI;
    protected Lista lista;
    protected Terminal llaveD;

    /**
     * Único contructor de la clase
     *
     * @param paquetes Paquetes
     * @param identificador Identificador
     * @param llaveI Llave izquierda
     * @param lista Lista
     * @param llaveD Llave derecha
     * @param coleccion Colección
     */
    public FuncionBloque(Paquetes paquetes, Terminal identificador, Terminal llaveI, Lista lista, Terminal llaveD, ColParentesis coleccion) {
        super(paquetes, identificador, coleccion);
        setLlaveI(llaveI);
        setLista(lista);
        setLlaveD(llaveD);
    }

    /**
     * Obtiene la llave izquierda
     *
     * @return Llave izquierda
     */
    public Terminal getLlaveI() {
        return llaveI;
    }

    /**
     * Establece la llave izquierda
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
     * Obtiene la llave derecha
     *
     * @return Llave derecha
     */
    public Terminal getLlaveD() {
        return llaveD;
    }

    /**
     * Establece la llave derecha
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
        return new Simbolo[]{paquetes, identificador, llaveI, lista, llaveD, coleccion};
    }

}
