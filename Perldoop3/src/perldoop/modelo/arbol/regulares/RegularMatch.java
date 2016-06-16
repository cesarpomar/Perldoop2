package perldoop.modelo.arbol.regulares;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -> regulares : expresion STR_REX M_REGEX
 *
 * @author César Pomar
 */
public final class RegularMatch extends Regulares{

    private Terminal expresion;
    private Terminal match;
    private Terminal regular;

    /**
     * Único contructor de la clase
     *
     * @param expresion Expresión
     * @param match Match
     * @param regular Expresión regular
     */
    public RegularMatch(Terminal expresion, Terminal match, Terminal regular) {
        setExpresion(expresion);
        setMatch(match);
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
     * Obtiene el match
     *
     * @return Match
     */
    public Terminal getMatch() {
        return match;
    }

    /**
     * Establece el match
     *
     * @param match Match
     */
    public void setMatch(Terminal match) {
        match.setPadre(this);
        this.match = match;
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
        return new Simbolo[]{expresion, match, regular};
    }
}
