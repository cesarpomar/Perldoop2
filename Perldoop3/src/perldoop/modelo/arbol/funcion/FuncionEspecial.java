package perldoop.modelo.arbol.funcion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.lista.Lista;

/**
 * Clase que representa la reduccion -&gt; funcion : ID '{' lista '}' expresion
 *
 * @author César Pomar
 */
public final class FuncionEspecial extends Funcion {

    private Terminal llaveI;
    private Lista lista;
    private Terminal llaveD;
    private ExpColeccion coleccion;

    /**
     * Único contructor de la clase
     *
     * @param identificador Identificador
     * @param llaveI Llave izquierda
     * @param lista Lista
     * @param llaveD Llave derecha
     * @param coleccion Colección
     */
    public FuncionEspecial(Terminal identificador, Terminal llaveI, Lista lista, Terminal llaveD, ExpColeccion coleccion) {
        super(identificador);
        setLlaveI(llaveI);
        setLista(lista);
        setLlaveD(llaveD);
        setColeccion(coleccion);
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

    /**
     * Obtiene la colección
     *
     * @return Colección
     */
    public ExpColeccion getColeccion() {
        return coleccion;
    }

    /**
     * Establece la colección
     *
     * @param coleccion Colección
     */
    public void setColeccion(ExpColeccion coleccion) {
        coleccion.setPadre(this);
        this.coleccion = coleccion;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{identificador, llaveI, lista, llaveD, coleccion};
    }

}
