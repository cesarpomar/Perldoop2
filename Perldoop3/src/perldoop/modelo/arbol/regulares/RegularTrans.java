package perldoop.modelo.arbol.regulares;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -> regulares : expresion STR_REX Y_REGEX
 *
 * @author César Pomar
 */
public final class RegularTrans extends Regulares {

    private Terminal expresion;
    private Terminal trans;
    private Terminal regular;

    /**
     * Único contructor de la clase
     *
     * @param expresion Expresión
     * @param trans Transposición
     * @param regular Expresión regular
     */
    public RegularTrans(Terminal expresion, Terminal trans, Terminal regular) {
        setExpresion(expresion);
        setTrans(trans);
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
     * Obtiene el transposición
     *
     * @return Transposición
     */
    public Terminal getTrans() {
        return trans;
    }

    /**
     * Establece el transposición
     *
     * @param trans Transposición
     */
    public void setTrans(Terminal trans) {
        trans.setPadre(this);
        this.trans = trans;
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
        return new Simbolo[]{expresion, trans, regular};
    }

}
