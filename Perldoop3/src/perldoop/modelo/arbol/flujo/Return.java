package perldoop.modelo.arbol.flujo;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; <br>
 * flujo : RETURN ';'<br>
 * | RETURN expresion ';'
 *
 * @author César Pomar
 */
public final class Return extends Flujo {

    private Terminal id;
    private Expresion expresion;
    private Terminal puntoComa;

    /**
     * Constructor de return con argumentos
     *
     * @param id Return
     * @param expresion Expresión
     * @param puntoComa PuntoComa
     */
    public Return(Terminal id, Expresion expresion, Terminal puntoComa) {
        setId(id);
        setExpresion(expresion);
        setPuntoComa(puntoComa);
    }

    /**
     * Constructor de return sin argumentos
     *
     * @param id Return
     * @param puntoComa PuntoComa
     */
    public Return(Terminal id, Terminal puntoComa) {
        setId(id);
        setPuntoComa(puntoComa);
    }

    /**
     * Obtiene el return
     *
     * @return Return
     */
    public Terminal getId() {
        return id;
    }

    /**
     * Establece el return
     *
     * @param id Return
     */
    public void setId(Terminal id) {
        id.setPadre(this);
        this.id = id;
    }

    /**
     * Obtiene la expresion
     *
     * @return Expresion
     */
    public Expresion getExpresion() {
        return expresion;
    }

    /**
     * Establece la expresion
     *
     * @param expresion Expresion
     */
    public void setExpresion(Expresion expresion) {
        expresion.setPadre(this);
        this.expresion = expresion;
    }

    /**
     * Obtiene el punto y coma ';'
     *
     * @return PuntoComa
     */
    public Terminal getPuntoComa() {
        return puntoComa;
    }

    /**
     * Establece el punto y coma ';'
     *
     * @param puntoComa PuntoComa
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
        if (expresion != null) {
            return new Simbolo[]{id, expresion, puntoComa};
        }else{
            return new Simbolo[]{id, puntoComa};
        }
    }

}
