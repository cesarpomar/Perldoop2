package perldoop.modelo.arbol.condicional;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.contexto.Contexto;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt;<br> condicional : Elsif '(' expresion ')' '{' cuerpo '}' condicional
 *
 * @author César Pomar
 */
public final class CondicionalElsif extends Condicional {

    private Terminal id;
    private Terminal parentesisI;
    private Expresion expresion;
    private Terminal parentesisD;
    private Contexto contexto;
    private Condicional bloqueElse;

    /**
     * Único contructor de la clase
     *
     * @param id elsIf
     * @param parentesisI Parentesis izquierdo
     * @param expresion Expresión
     * @param parentesisD Parentesis derecho
     * @param contexto Contexto
     * @param bloqueElse BloqueElse
     */
    public CondicionalElsif(Terminal id, Terminal parentesisI, Expresion expresion, Terminal parentesisD, Contexto contexto, Condicional bloqueElse) {
        setId(id);
        setParentesisI(parentesisI);
        setExpresion(expresion);
        setParentesisD(parentesisD);
        setContexto(contexto);
        setBloqueElse(bloqueElse);
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
     * Obtiene el contexto del bloque
     *
     * @return Contexto del bloque
     */
    public final Contexto getContexto() {
        return contexto;
    }

    /**
     * Establece el contexto del bloque
     *
     * @param contexto Contexto del bloque
     */
    public final void setContexto(Contexto contexto) {
        contexto.setPadre(this);
        this.contexto = contexto;
    }

    /**
     * Obtiene el bloque Else
     *
     * @return Bloque Else
     */
    public Condicional getBloqueElse() {
        return bloqueElse;
    }

    /**
     * Establece el bloque Else
     *
     * @param bloqueElse Bloque Else
     */
    public void setBloqueElse(Condicional bloqueElse) {
        bloqueElse.setPadre(this);
        this.bloqueElse = bloqueElse;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{id, parentesisI, expresion, parentesisD, contexto, bloqueElse};
    }
}
