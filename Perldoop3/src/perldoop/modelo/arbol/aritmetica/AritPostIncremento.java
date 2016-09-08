package perldoop.modelo.arbol.aritmetica;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; aritmetica : expresion MAS_MAS
 *
 * @author César Pomar
 */
public final class AritPostIncremento extends Aritmetica {

    private Expresion expresion;

    /**
     * Único contructor de la clase
     *
     * @param expresion Expresión
     * @param operador Operador
     */
    public AritPostIncremento(Expresion expresion, Terminal operador) {
        super(operador);
        setExpresion(expresion);
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

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{expresion, operador};
    }
}
