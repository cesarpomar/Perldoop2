package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt;<br> bloque : FOR '(' expresion ';' expresion ';' expresion ')' '{' cuerpo '}'
 *
 * @author César Pomar
 */
public final class BloqueFor extends Bloque {

    private Terminal id;
    protected AbrirBloque abrirBloque;
    private Terminal parentesisI;
    private Expresion expresion1;
    private Terminal puntoComa1;
    private Expresion expresion2;
    private Terminal puntoComa2;
    private Expresion expresion3;
    private Terminal parentesisD;

    /**
     * Único contructor de la clase
     *
     * @param id Id
     * @param abrirBloque Abertura de bloque para la cabecera
     * @param parentesisI Parentesis izquierdo
     * @param expresion1 Expresión 1
     * @param puntoComa1 Punto y coma 1
     * @param expresion2 Expresión 2
     * @param puntoComa2 Punto y coma 2
     * @param expresion3 Expresión 3
     * @param parentesisD Parentesis derecho
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     */
    public BloqueFor(Terminal id, AbrirBloque abrirBloque, Terminal parentesisI, Expresion expresion1, Terminal puntoComa1, Expresion expresion2, Terminal puntoComa2, Expresion expresion3, Terminal parentesisD, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD) {
        super(llaveI, cuerpo, llaveD);
        setId(id);
        setAbrirBloque(abrirBloque);
        setParentesisI(parentesisI);
        setExpresion1(expresion1);
        setPuntoComa1(puntoComa1);
        setExpresion2(expresion2);
        setPuntoComa2(puntoComa2);
        setExpresion3(expresion3);
        setParentesisI(parentesisI);
    }

    /**
     * Obtiene el id de bloque
     *
     * @return Id
     */
    public final Terminal getId() {
        return id;
    }

    /**
     * Establece el id de bloque
     *
     * @param id Id
     */
    public final void setId(Terminal id) {
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
     * Obtiene la expresión 1
     *
     * @return Expresión 1
     */
    public Expresion getExpresion1() {
        return expresion1;
    }

    /**
     * Establece la expresión 1
     *
     * @param expresion1 Expresión 1
     */
    public void setExpresion1(Expresion expresion1) {
        expresion1.setPadre(this);
        this.expresion1 = expresion1;
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
     * Obtiene la expresión 2
     *
     * @return Expresión 2
     */
    public Expresion getExpresion2() {
        return expresion2;
    }

    /**
     * Establece la expresión 2
     *
     * @param expresion2 Expresión 2
     */
    public void setExpresion2(Expresion expresion2) {
        expresion2.setPadre(this);
        this.expresion2 = expresion2;
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
     * Obtiene la expresión 3
     *
     * @return Expresión 3
     */
    public Expresion getExpresion3() {
        return expresion3;
    }

    /**
     * Establece la expresión 3
     *
     * @param expresion3 Expresión 3
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

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{id, parentesisI, expresion1, puntoComa1, expresion2, puntoComa2, expresion3, parentesisD, llaveI, cuerpo, llaveD};
    }

}
