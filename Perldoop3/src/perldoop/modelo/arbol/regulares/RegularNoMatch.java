package perldoop.modelo.arbol.regulares;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -> regulares : expresion STR_NO_REX M_REGEX
 *
 * @author César Pomar
 */
public final class RegularNoMatch extends Regulares{

    private Terminal expresion;
    private Terminal noMatch;
    private Terminal regular;

    /**
     * Único contructor de la clase
     *
     * @param expresion Expresión
     * @param noMatch No Match
     * @param regular Expresión regular
     */
    public RegularNoMatch(Terminal expresion, Terminal noMatch, Terminal regular) {
        setExpresion(expresion);
        setNoMatch(noMatch);
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
     * Obtiene el no Match
     *
     * @return No match
     */
    public Terminal getNoMatch() {
        return noMatch;
    }

    /**
     * Establece el noMatch
     *
     * @param noMatch No match
     */
    public void setNoMatch(Terminal noMatch) {
        noMatch.setPadre(this);
        this.noMatch = noMatch;
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
        return new Simbolo[]{expresion, noMatch, regular};
    }
}
