package perldoop.modelo.arbol.modificador;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; modificador : FOR expresion
 *
 * @author César Pomar
 */
public final class ModFor extends Modificador {

    private Terminal forT;
    private Expresion expresion;

    /**
     * Único contructor de la clase
     *
     * @param forT For
     * @param expresion Expresión
     */
    public ModFor(Terminal forT, Expresion expresion) {
        setForT(forT);
        setExpresion(expresion);
    }

    /**
     * Obtiene el for
     *
     * @return For
     */
    public Terminal getForT() {
        return forT;
    }

    /**
     * Estabelce el for
     *
     * @param forT For
     */
    public void setForT(Terminal forT) {
        forT.setPadre(this);
        this.forT = forT;
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
        return new Simbolo[]{forT, expresion};
    }
}
