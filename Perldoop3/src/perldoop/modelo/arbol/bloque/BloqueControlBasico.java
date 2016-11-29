package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase abtracta que representa todos los bloques basicos de control
 *
 * @author César Pomar
 */
public abstract class BloqueControlBasico extends Bloque {

    protected Terminal id;
    protected AbrirBloque contextoHead;
    protected Terminal parentesisI;
    protected Expresion expresion;
    protected Terminal parentesisD;

    /**
     * Único contructor de la clase
     *
     * @param id Id
     * @param contextoHead Contexto cabecera
     * @param parentesisI Parentesis izquierdo
     * @param expresion Expresión
     * @param parentesisD Parentesis derecho
     * @param contextoBloque Contexto bloque
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     */
    public BloqueControlBasico(Terminal id, AbrirBloque contextoHead, Terminal parentesisI, Expresion expresion, Terminal parentesisD, AbrirBloque contextoBloque, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD) {
        super(contextoBloque, llaveI, cuerpo, llaveD);
        setId(id);
        setContextoHead(contextoHead);
        setParentesisI(parentesisI);
        setExpresion(expresion);
        setParentesisD(parentesisD);
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
     * Obtiene el contexto de la cabecera
     *
     * @return Contexto de la cabecera
     */
    public final AbrirBloque getContextoHead() {
        return contextoHead;
    }

    /**
     * Establece el contexto de la cabecera
     *
     * @param contextoHead Contexto de la cabecera
     */
    public final void setContextoHead(AbrirBloque contextoHead) {
        contextoHead.setPadre(this);
        this.contextoHead = contextoHead;
    }

    /**
     * Obtiene el parenteis izquierdo
     *
     * @return Parenteis izquierdo
     */
    public final Terminal getParentesisI() {
        return parentesisI;
    }

    /**
     * Establece el parenteis izquierdo
     *
     * @param parentesisI Parenteis izquierdo
     */
    public final void setParentesisI(Terminal parentesisI) {
        parentesisI.setPadre(this);
        this.parentesisI = parentesisI;
    }

    /**
     * Obtiene la expresión
     *
     * @return Expresión
     */
    public final Expresion getExpresion() {
        return expresion;
    }

    /**
     * Establece la expresión
     *
     * @param expresion Expresión
     */
    public final void setExpresion(Expresion expresion) {
        expresion.setPadre(this);
        this.expresion = expresion;
    }

    /**
     * Obtiene el parentesis derecho
     *
     * @return Parentesis derecho
     */
    public final Terminal getParentesisD() {
        return parentesisD;
    }

    /**
     * Establece el parentesis derecho
     *
     * @param parentesisD Parentesis derecho
     */
    public final void setParentesisD(Terminal parentesisD) {
        parentesisD.setPadre(this);
        this.parentesisD = parentesisD;
    }

}
