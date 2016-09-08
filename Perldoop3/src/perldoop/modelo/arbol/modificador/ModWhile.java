package perldoop.modelo.arbol.modificador;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; modificador : WHILE expresion
 *
 * @author César Pomar
 */
public final class ModWhile extends Modificador {

    private Terminal whileT;
    private Expresion expresion;

    /**
     * Único contructor de la clase
     *
     * @param whileT While
     * @param expresion Expresión
     */
    public ModWhile(Terminal whileT, Expresion expresion) {
        setWhileT(whileT);
        setExpresion(expresion);
    }

    /**
     * Obtiene el while
     *
     * @return While
     */
    public Terminal getWhileT() {
        return whileT;
    }

    /**
     * Establece el while
     *
     * @param whileT While
     */
    public void setWhileT(Terminal whileT) {
        whileT.setPadre(this);
        this.whileT = whileT;
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
        return new Simbolo[]{whileT, expresion};
    }
}
