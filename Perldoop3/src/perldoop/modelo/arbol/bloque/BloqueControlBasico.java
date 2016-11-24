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
    protected AbrirBloque abrirBloque;
    protected Terminal parentesisI;
    protected Expresion expresion;
    protected Terminal parentesisD;

    /**
     * Único contructor de la clase
     *
     * @param id Id
     * @param abrirBloque Abertura de bloque para la cabecera
     * @param parentesisI Parentesis izquierdo
     * @param expresion Expresión
     * @param parentesisD Parentesis derecho
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     */
    public BloqueControlBasico(Terminal id, AbrirBloque abrirBloque, Terminal parentesisI, Expresion expresion, Terminal parentesisD, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD) {
        super(llaveI, cuerpo, llaveD);
        setId(id);
        setAbrirBloque(abrirBloque);
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
     * Obtiene la abertura de bloque
     *
     * @return Abertura de bloque
     */
    public final AbrirBloque getAbrirBloque() {
        return abrirBloque;
    }

    /**
     * Establece la abertura de bloque
     *
     * @param abrirBloque Abertura de bloque
     */
    public final void setAbrirBloque(AbrirBloque abrirBloque) {
        abrirBloque.setPadre(this);
        this.abrirBloque = abrirBloque;
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
