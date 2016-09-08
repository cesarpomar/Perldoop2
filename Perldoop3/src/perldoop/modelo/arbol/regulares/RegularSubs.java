package perldoop.modelo.arbol.regulares;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; regulares : expresion STR_REX S_REGEX
 *
 * @author César Pomar
 */
public final class RegularSubs extends Regulares {

    private Terminal expresion;
    private Terminal subs;
    private Terminal regular;

    /**
     * Único contructor de la clase
     *
     * @param expresion Expresión
     * @param subs Substitución
     * @param regular Expresión regular
     */
    public RegularSubs(Terminal expresion, Terminal subs, Terminal regular) {
        setExpresion(expresion);
        setSubs(subs);
        setRegular(regular);
    }

    /**
     * Obtiene la expresión
     *
     * @return Expresión
     */
    public Terminal getExpresion() {
        return expresion;
    }

    /**
     * Establece la expresión
     *
     * @param expresion Expresión
     */
    public void setExpresion(Terminal expresion) {
        expresion.setPadre(this);
        this.expresion = expresion;
    }

    /**
     * Obtiene el substitución
     *
     * @return Substitución
     */
    public Terminal getSubs() {
        return subs;
    }

    /**
     * Establece el substitución
     *
     * @param subs Substitución
     */
    public void setSubs(Terminal subs) {
        subs.setPadre(this);
        this.subs = subs;
    }

    /**
     * Obtiene la expresión regular
     *
     * @return Expresión regular
     */
    public Terminal getRegular() {
        return regular;
    }

    /**
     * Establece la expresión regular
     *
     * @param regular Expresión regular
     */
    public void setRegular(Terminal regular) {
        regular.setPadre(this);
        this.regular = regular;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{expresion, subs, regular};
    }
}
