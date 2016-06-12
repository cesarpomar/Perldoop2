package perldoop.modelo.arbol.modificador;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -> modificador : IF expresion
 *
 * @author César Pomar
 */
public final class ModIf extends Modificador {

    private Terminal ifT;
    private Expresion expresion;

    /**
     * Único contructor de la clase
     *
     * @param ifT If
     * @param expresion Expresión
     */
    public ModIf(Terminal ifT, Expresion expresion) {
        setIfT(ifT);
        setExpresion(expresion);
    }

    /**
     * Obtiene el if
     *
     * @return If
     */
    public Terminal getIfT() {
        return ifT;
    }

    /**
     * Estabelce el if
     *
     * @param ifT If
     */
    public void setIfT(Terminal ifT) {
        ifT.setPadre(this);
        this.ifT = ifT;
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
        return new Simbolo[]{ifT, expresion};
    }
}
