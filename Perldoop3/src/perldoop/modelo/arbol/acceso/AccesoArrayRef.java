package perldoop.modelo.arbol.acceso;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.lista.Lista;

/**
 * Clase que representa la reduccion -> acceso : expresion FLECHA '[' lista ']'
 *
 * @author César Pomar
 */
public final class AccesoArrayRef extends Acceso {

    private Expresion expresion;
    private Terminal flecha;
    private Terminal corcheteI;
    private Lista lista;
    private Terminal corcheteD;

    /**
     * Único contructor de la clase
     *
     * @param expresion Expresión
     * @param flecha Flecha
     * @param corcheteI Corchete izquierdo
     * @param lista Lista
     * @param corcheteD Corchete derecho
     */
    public AccesoArrayRef(Expresion expresion, Terminal flecha, Terminal corcheteI, Lista lista, Terminal corcheteD) {
        setExpresion(expresion);
        setFlecha(flecha);
        setCorcheteI(corcheteI);
        setLista(lista);
        setCorcheteD(corcheteD);
    }

    /**
     * Obtiene la expresión
     *
     * @return Expresión
     */
    public Expresion getExpresion() {
        return expresion;
    }

    /**
     * Establece la expresión
     *
     * @param expresion Expresión
     */
    public void setExpresion(Expresion expresion) {
        expresion.setPadre(this);
        this.expresion = expresion;
    }

    /**
     * Obtiene la flecha
     *
     * @return Flecha
     */
    public Terminal getFlecha() {
        return flecha;
    }

    /**
     * Establece la flecha
     *
     * @param flecha Flecha
     */
    public void setFlecha(Terminal flecha) {
        flecha.setPadre(this);
        this.flecha = flecha;
    }

    /**
     * Obtiene el corchete izquierdo
     *
     * @return Corchete izquierdo
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
     * Obtiene el corchete derecho
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
        lista.setPadre(this);
        this.corcheteD = corcheteD;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{expresion, flecha, corcheteI, lista, corcheteD};
    }
}
