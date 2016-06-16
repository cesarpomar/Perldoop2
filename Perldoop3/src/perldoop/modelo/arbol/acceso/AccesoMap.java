package perldoop.modelo.arbol.acceso;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.lista.Lista;

/**
 * Clase que representa la reduccion -> acceso : expresion '{' lista '}'
 *
 * @author César Pomar
 */
public final class AccesoMap extends Acceso {

    private Terminal llaveI;
    private Lista lista;
    private Terminal llaveD;

    /**
     * Único contructor de la clase
     *
     * @param expresion Expresión
     * @param llaveI Llave izquierda
     * @param lista Lista
     * @param llaveD Llave derecha
     */
    public AccesoMap(Expresion expresion, Terminal llaveI, Lista lista, Terminal llaveD) {
        super(expresion);
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
     * @param llaveI llave izquierda
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
        lista.setPadre(this);
        this.llaveD = llaveD;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{expresion, llaveI, lista, llaveD};
    }
}
