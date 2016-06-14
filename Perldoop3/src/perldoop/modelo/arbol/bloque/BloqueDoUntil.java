package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -><br> bloque : DO abrirBloque '{' cuerpo
 * '}' UNTIL '(' expresion ')' ';'
 *
 * @author César Pomar
 */
public final class BloqueDoUntil extends Bloque {

    private Terminal doT;
    private AbrirBloque abrirBloque;
    private Terminal llaveI;
    private Cuerpo cuerpo;
    private Terminal llaveD;
    private Terminal parentesisI;
    private Expresion expresion;
    private Terminal parentesisD;
    private Terminal untilT;
    private Terminal puntoComa;

    /**
     * Único contructor de la clase
     *
     * @param doT Do
     * @param abrirBloque AbrirBloque
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     * @param parentesisI Parentesis izquierdo
     * @param expresion Expresiom
     * @param parentesisD Parentesis derecho
     * @param untilT Until
     * @param puntoComa Punto y coma
     */
    public BloqueDoUntil(Terminal doT, AbrirBloque abrirBloque, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD, Terminal parentesisI, Expresion expresion, Terminal parentesisD, Terminal untilT, Terminal puntoComa) {
        setDoT(doT);
        setAbrirBloque(abrirBloque);
        setLlaveI(llaveI);
        setCuerpo(cuerpo);
        setLlaveD(llaveD);
        setParentesisI(parentesisI);
        setExpresion(expresion);
        setParentesisD(parentesisD);
        setUntilT(untilT);
        setPuntoComa(puntoComa);
    }

    /**
     * Obtiene el do
     *
     * @return Do
     */
    public Terminal getDoT() {
        return doT;
    }

    /**
     * Establece el do
     *
     * @param doT Do
     */
    public void setDoT(Terminal doT) {
        doT.setPadre(this);
        this.doT = doT;
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
     * Obtiene el while
     *
     * @return Until
     */
    public Terminal getUntilT() {
        return untilT;
    }

    /**
     * Establece el while
     *
     * @param untilT Until
     */
    public void setUntilT(Terminal untilT) {
        untilT.setPadre(this);
        this.untilT = untilT;
    }

    /**
     * Obtiene el punto y coma
     *
     * @return Punto y coma
     */
    public Terminal getPuntoComa() {
        return puntoComa;
    }

    /**
     * Establece el punto y coma
     *
     * @param puntoComa Punto y coma
     */
    public void setPuntoComa(Terminal puntoComa) {
        puntoComa.setPadre(this);
        this.puntoComa = puntoComa;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{doT, abrirBloque, llaveI, cuerpo, llaveD, parentesisI, expresion, parentesisD, puntoComa};
    }
}
