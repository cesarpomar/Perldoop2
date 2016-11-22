package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.contexto.Contexto;
import perldoop.modelo.arbol.lista.Lista;

/**
 * Clase que representa la reduccion -&gt;<br> bloque : FOR '(' listaFor ';' listaFor ';' listaFor ')' '{' cuerpo '}'
 *
 * @author César Pomar
 */
public final class BloqueFor extends Bloque {

    private Terminal id;
    protected AbrirBloque abrirBloque;
    private Terminal parentesisI;
    private Lista lista1;
    private Terminal puntoComa1;
    private Lista lista2;
    private Terminal puntoComa2;
    private Lista lista3;
    private Terminal parentesisD;

    /**
     * Único contructor de la clase
     *
     * @param id Id
     * @param abrirBloque Abertura de bloque para la cabecera
     * @param parentesisI Parentesis izquierdo
     * @param lista1 Lista 1
     * @param puntoComa1 Punto y coma 1
     * @param lista2 Lista 2
     * @param puntoComa2 Punto y coma 2
     * @param lista3 Lista 3
     * @param parentesisD Parentesis derecho
     * @param contexto Contexto del bloque
     */
    public BloqueFor(Terminal id, AbrirBloque abrirBloque, Terminal parentesisI, Lista lista1, Terminal puntoComa1, Lista lista2, Terminal puntoComa2, Lista lista3, Terminal parentesisD, Contexto contexto) {
        super(contexto);
        setId(id);
        setAbrirBloque(abrirBloque);
        setParentesisI(parentesisI);
        setLista1(lista1);
        setPuntoComa1(puntoComa1);
        setLista2(lista2);
        setPuntoComa2(puntoComa2);
        setLista3(lista3);
        setParentesisD(parentesisD);
    }

    /**
     * Obtiene el id de bloque
     *
     * @return Id
     */
    public Terminal getId() {
        return id;
    }

    /**
     * Establece el id de bloque
     *
     * @param id Id
     */
    public void setId(Terminal id) {
        id.setPadre(this);
        this.id = id;
    }
    /**
     * Obtiene la abertura de bloque
     *
     * @return Abertura de bloque
     */
    public AbrirBloque getAbrirBloque() {
        return abrirBloque;
    }

    /**
     * Establece la abertura de bloque
     *
     * @param abrirBloque Abertura de bloque
     */
    public void setAbrirBloque(AbrirBloque abrirBloque) {
        abrirBloque.setPadre(this);
        this.abrirBloque = abrirBloque;
    }
    /**
     * Obtiene el parenteis izquierdo
     *
     * @return Parenteis izquierdo
     */
    public Terminal getParentesisI() {
        return parentesisI;
    }

    /**
     * Establece el parenteis izquierdo
     *
     * @param parentesisI Parenteis izquierdo
     */
    public void setParentesisI(Terminal parentesisI) {
        parentesisI.setPadre(this);
        this.parentesisI = parentesisI;
    }

    /**
     * Obtiene la lista 1
     *
     * @return Lista 1
     */
    public Lista getLista1() {
        return lista1;
    }

    /**
     * Establece la lista 1
     *
     * @param lista1 Lista 1
     */
    public void setLista1(Lista lista1) {
        lista1.setPadre(this);
        this.lista1 = lista1;
    }

    /**
     * Obtiene el punto y coma 1
     *
     * @return Punto y coma 1
     */
    public Terminal getPuntoComa1() {
        return puntoComa1;
    }

    /**
     * Establece el punto y coma 1
     *
     * @param puntoComa1 Punto y coma 1
     */
    public void setPuntoComa1(Terminal puntoComa1) {
        puntoComa1.setPadre(this);
        this.puntoComa1 = puntoComa1;
    }

    /**
     * Obtiene la lista 2
     *
     * @return Lista 2
     */
    public Lista getLista2() {
        return lista2;
    }

    /**
     * Establece la lista 2
     *
     * @param lista2 Lista 2
     */
    public void setLista2(Lista lista2) {
        lista2.setPadre(this);
        this.lista2 = lista2;
    }

    /**
     * Obtiene el punto y coma 2
     *
     * @return Punto y coma 2
     */
    public Terminal getPuntoComa2() {
        return puntoComa2;
    }

    /**
     * Establece el punto y coma 2
     *
     * @param puntoComa2 Punto y coma 2
     */
    public void setPuntoComa2(Terminal puntoComa2) {
        puntoComa2.setPadre(this);
        this.puntoComa2 = puntoComa2;
    }

    /**
     * Obtiene la lista 3
     *
     * @return Lista 3
     */
    public Lista getLista3() {
        return lista3;
    }

    /**
     * Establece la lista 3
     *
     * @param lista3 Lista 3
     */
    public void setLista3(Lista lista3) {
        lista3.setPadre(this);
        this.lista3 = lista3;
    }

    /**
     * Obtiene el parentesis derecho
     *
     * @return Parentesis derecho
     */
    public Terminal getParentesisD() {
        return parentesisD;
    }

    /**
     * Establece el parentesis derecho
     *
     * @param parentesisD Parentesis derecho
     */
    public void setParentesisD(Terminal parentesisD) {
        parentesisD.setPadre(this);
        this.parentesisD = parentesisD;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{id, parentesisI, lista1, puntoComa1, lista2, puntoComa2, lista3, parentesisD, contexto};
    }

}
