package perldoop.modelo.arbol.modificador;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; modificador : UNLESS expresion
 *
 * @author César Pomar
 */
public final class ModUnless extends Modificador {

    private Terminal unlessT;
    private Expresion expresion;

    /**
     * Único contructor de la clase
     *
     * @param unless Unless
     * @param expresion Expresión
     */
    public ModUnless(Terminal unless, Expresion expresion) {
        setUnlessT(unless);
        setExpresion(expresion);
    }

    /**
     * Obtiene el unless
     *
     * @return Unless
     */
    public Terminal getUnlessT() {
        return unlessT;
    }

    /**
     * Establece el unless
     *
     * @param unlessT Unless
     */
    public void setUnlessT(Terminal unlessT) {
        unlessT.setPadre(this);
        this.unlessT = unlessT;
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
        return new Simbolo[]{unlessT, expresion};
    }

}
