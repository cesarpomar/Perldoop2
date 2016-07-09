package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.lista.Lista;

/**
 * Clase que representa la reduccion -><br> bloque : FOR abrirBloque expresion '(' lista ')' '{' cuerpo '}'
 *
 * @author César Pomar
 */
public final class BloqueForeachVar extends Bloque {

    private Terminal forT;
    private AbrirBloque abrirBloque;
    private Expresion expresion;
    private Terminal parentesisI;
    private Lista lista;
    private Terminal parentesisD;
    private Terminal llaveI;
    private Cuerpo cuerpo;
    private Terminal llaveD;

    /**
     * Único contructor de la clase
     *
     * @param forT For
     * @param abrirBloque AbrirBloque
     * @param expresion Expresión
     * @param parentesisI Parentesis izquierdo
     * @param lista Lista
     * @param parentesisD Parentesis derecho
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     */
    public BloqueForeachVar(Terminal forT, AbrirBloque abrirBloque, Expresion expresion, Terminal parentesisI, Lista lista, Terminal parentesisD, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD) {
        setForT(forT);
        setAbrirBloque(abrirBloque);
        setExpresion(expresion);
        setParentesisI(parentesisI);
        setLista(lista);
        setParentesisD(parentesisD);
        setLlaveI(llaveI);
        setCuerpo(cuerpo);
        setLlaveD(llaveD);
    }

    /**
     * Obtiene el for
     *
     * @return For
     */
    public Terminal getForT() {
        return forT;
    }

    /**
     * Establece el for
     *
     * @param forT For
     */
    public void setForT(Terminal forT) {
        forT.setPadre(this);
        this.forT = forT;
    }

    /**
     * Obtiene el abrirBloque
     *
     * @return AbrirBloque
     */
    public AbrirBloque getAbrirBloque() {
        return abrirBloque;
    }

    /**
     * Establece el abrirBloque
     *
     * @param abrirBloque AbrirBloque
     */
    public void setAbrirBloque(AbrirBloque abrirBloque) {
        abrirBloque.setPadre(this);
        this.abrirBloque = abrirBloque;
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
     * Obtiene el cuerpo
     *
     * @return Cuerpo
     */
    public Cuerpo getCuerpo() {
        return cuerpo;
    }

    /**
     * Establece el cuerpo
     *
     * @param cuerpo Cuerpo
     */
    public void setCuerpo(Cuerpo cuerpo) {
        cuerpo.setPadre(this);
        this.cuerpo = cuerpo;
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
        return new Simbolo[]{forT, abrirBloque, expresion, parentesisI, lista, parentesisD, llaveI, cuerpo, llaveD};
    }
}
