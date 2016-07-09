package perldoop.modelo.arbol.flujo;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -> <br>
 * flujo : RETURN ';'<br>
 | RETURN expresion ';'
 *
 * @author César Pomar
 */
public final class Return extends Flujo {

    private Terminal returnF;
    private Expresion expresion;
    private Terminal puntoComa;

    /**
     * Único contructor de la clase
     *
     * @param returnF Return
     * @param expresion Expresión
     * @param puntoComa PuntoComa
     */
    public Return(Terminal returnF, Expresion expresion, Terminal puntoComa) {
        setReturnF(returnF);
        setExpresion(expresion);
        setPuntoComa(puntoComa);
    }

    /**
     * Obtiene el return
     *
     * @return Return
     */
    public Terminal getReturnF() {
        return returnF;
    }

    /**
     * Establece el return
     *
     * @param returnF Return
     */
    public void setReturnF(Terminal returnF) {
        returnF.setPadre(this);
        this.returnF = returnF;
    }

    /**
     * Obtiene la expresion
     *
     * @return Expresion
     */
    public final Expresion getExpresion() {
        return expresion;
    }

    /**
     * Establece la expresion
     *
     * @param expresion Expresion
     */
    public final void setExpresion(Expresion expresion) {
        expresion.setPadre(this);
        this.expresion = expresion;
    }

    /**
     * Obtiene el punto y coma ';'
     *
     * @return PuntoComa
     */
    public final Terminal getPuntoComa() {
        return puntoComa;
    }

    /**
     * Establece el punto y coma ';'
     *
     * @param puntoComa PuntoComa
     */
    public final void setPuntoComa(Terminal puntoComa) {
        puntoComa.setPadre(this);
        this.puntoComa = puntoComa;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{returnF};
    }

}
