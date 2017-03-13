package perldoop.modelo.arbol.flujo;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; <br>
 * flujo : RETURN<br>
 * | RETURN expresion
 *
 * @author César Pomar
 */
public final class Return extends Flujo {

    private Expresion expresion;

    /**
     * Constructor de return sin argumentos
     *
     * @param id Return
     */
    public Return(Terminal id) {
        super(id);
    }

    /**
     * Constructor de return con argumentos
     *
     * @param id Return
     * @param expresion Expresión
     */
    public Return(Terminal id, Expresion expresion) {
        this(id);
        setExpresion(expresion);
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

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        if (expresion != null) {
            return new Simbolo[]{id, expresion};
        } else {
            return new Simbolo[]{id};
        }
    }

}
