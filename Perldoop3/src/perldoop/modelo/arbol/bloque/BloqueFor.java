package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -><br> bloque : FOR abrirBloque '('
 * expresion ';' expresion ';' expresion ')' '{' cuerpo '}'
 *
 * @author César Pomar
 */
public final class BloqueFor extends Bloque {

    private Terminal forT;
    private AbrirBloque abrirBloque;
    private Terminal parentesisI;
    private Expresion expresion1;
    private Terminal puntoComa1;
    private Expresion expresion2;
    private Terminal puntoComa2;
    private Expresion expresion3;
    private Terminal parentesisD;
    private Terminal llaveI;
    private Cuerpo cuerpo;
    private Terminal llaveD;

    public BloqueFor(Terminal forT, AbrirBloque abrirBloque, Terminal parentesisI, Expresion expresion1, Terminal puntoComa1, Expresion expresion2, Terminal puntoComa2, Expresion expresion3, Terminal parentesisD, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD) {
        setForT(forT);
        setAbrirBloque(abrirBloque);
        setParentesisI(parentesisI);
        setExpresion1(expresion1);
        setPuntoComa1(puntoComa1);
        setExpresion2(expresion2);
        setPuntoComa2(puntoComa2);
        setExpresion3(expresion3);
        setParentesisI(parentesisI);
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
     * Obtiene el punto y coma1
     *
     * @return Punto y coma1
     */
    public Terminal getPuntoComa1() {
        return puntoComa1;
    }

    /**
     * Establece el punto y coma1
     *
     * @param puntoComa1 Punto y coma1
     */
    public void setPuntoComa1(Terminal puntoComa1) {
        puntoComa1.setPadre(this);
        this.puntoComa1 = puntoComa1;
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
     * Obtiene el punto y coma2
     *
     * @return Punto y coma2
     */
    public Terminal getPuntoComa2() {
        return puntoComa2;
    }

    /**
     * Establece el punto y coma2
     *
     * @param puntoComa2 Punto y coma2
     */
    public void setPuntoComa2(Terminal puntoComa2) {
        puntoComa2.setPadre(this);
        this.puntoComa2 = puntoComa2;
    }

    /**
     * Obtiene la expresión3
     *
     * @return Expresión3
     */
    public Expresion getExpresion3() {
        return expresion3;
    }

    /**
     * Establece la expresión3
     *
     * @param expresion3 Expresión3
     */
    public void setExpresion3(Expresion expresion3) {
        expresion3.setPadre(this);
        this.expresion3 = expresion3;
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
        return new Simbolo[]{forT, abrirBloque, parentesisI, expresion1, puntoComa1, expresion2, puntoComa2, expresion3, parentesisD, llaveI, cuerpo, llaveD};
    }

}
