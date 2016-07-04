package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -><br> bloque : FOR abrirBloque expresion
 * '(' expresion ')' '{' cuerpo '}'
 *
 * @author César Pomar
 */
public final class BloqueForeachVar extends Bloque {

    private Terminal forT;
    private AbrirBloque abrirBloque;
    private Expresion expresion1;
    private Terminal parentesisI;
    private Expresion expresion2;
    private Terminal parentesisD;
    private Terminal llaveI;
    private Cuerpo cuerpo;
    private Terminal llaveD;

    /**
     * Único contructor de la clase
     *
     * @param forT For
     * @param abrirBloque AbrirBloque
     * @param expresion1 Expresión1
     * @param parentesisI Parentesis izquierdo
     * @param expresion2 Expresión2
     * @param parentesisD Parentesis derecho
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     */
    public BloqueForeachVar(Terminal forT, AbrirBloque abrirBloque, Expresion expresion1, Terminal parentesisI, Expresion expresion2, Terminal parentesisD, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD) {
        setForT(forT);
        setAbrirBloque(abrirBloque);
        setExpresion1(expresion1);
        setParentesisI(parentesisI);
        setExpresion2(expresion2);
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
     * Obtiene la expresión1
     *
     * @return Expresión1
     */
    public Expresion getExpresion1() {
        return expresion1;
    }

    /**
     * Establece la expresión1
     *
     * @param expresion1 Expresión1
     */
    public void setExpresion1(Expresion expresion1) {
        expresion1.setPadre(this);
        this.expresion1 = expresion1;
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
     * Obtiene la expresión2
     *
     * @return Expresión2
     */
    public Expresion getExpresion2() {
        return expresion2;
    }

    /**
     * Establece la expresión2
     *
     * @param expresion2 Expresión2
     */
    public void setExpresion2(Expresion expresion2) {
        expresion2.setPadre(this);
        this.expresion2 = expresion2;
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
        return new Simbolo[]{forT, abrirBloque, expresion1, parentesisI, expresion2, parentesisD, llaveI, cuerpo, llaveD};
    }
}
