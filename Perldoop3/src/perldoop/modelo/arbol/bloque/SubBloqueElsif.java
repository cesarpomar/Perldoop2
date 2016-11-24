package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt;<br> bloque : ELSIF '(' expresion ')' '{' cuerpo '}' subBloque
 *
 * @author César Pomar
 */
public final class SubBloqueElsif extends SubBloque {

    private Terminal id;
    private Terminal parentesisI;
    private Expresion expresion;
    private Terminal parentesisD;
    private SubBloque subBloque;

    /**
     * Único contructor de la clase
     *
     * @param id Id
     * @param parentesisI Parentesis izquierdo
     * @param expresion Expresión
     * @param parentesisD Parentesis derecho
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     * @param subBloque SubBloque
     */
    public SubBloqueElsif(Terminal id, Terminal parentesisI, Expresion expresion, Terminal parentesisD, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD, SubBloque subBloque) {
        super(llaveI, cuerpo, llaveD);
        setId(id);
        setParentesisI(parentesisI);
        setExpresion(expresion);
        setParentesisD(parentesisD);
        setSubBloque(subBloque);
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
     * Obtiene el subBloque
     *
     * @return SubBloque
     */
    public SubBloque getSubBloque() {
        return subBloque;
    }

    /**
     * Establece el subBloque
     *
     * @param subBloque SubBloque
     */
    public void setSubBloque(SubBloque subBloque) {
        subBloque.setPadre(this);
        this.subBloque = subBloque;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{id, parentesisI, expresion, parentesisD, llaveI, cuerpo, llaveD, subBloque};
    }
}
