package perldoop.modelo.arbol.logico;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; logico : '!' expresion
 *
 * @author César Pomar
 */
public final class LogNot extends Logico {

    private Expresion expresion;

    /**
     * Único contructor de la clase
     *
     * @param operador Operador
     * @param expresion Expresión
     */
    public LogNot(Terminal operador, Expresion expresion) {
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
        return new Simbolo[]{operador, expresion};
    }
}
