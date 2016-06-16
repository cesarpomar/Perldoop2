package perldoop.modelo.arbol.funcion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -> <br>
 * variable : paquete ID expresion<br>
 * | ID expresion<br>
 *
 * @author César Pomar
 */
public final class PaqueteFuncionArgs extends Funcion {

    private Terminal paquete;
    private Terminal ambito;
    private Expresion expresion;

    /**
     * Único contructor de la clase
     *
     * @param paquete Paquete
     * @param ambito Ambito
     * @param identificador Identificador
     * @param expresion Expresión
     */
    public PaqueteFuncionArgs(Terminal paquete, Terminal ambito, Terminal identificador, Expresion expresion) {
        super(identificador);
        setPaquete(paquete);
        setAmbito(ambito);
        setExpresion(expresion);
    }

    /**
     * Obtiene el paquete
     *
     * @return Paquete
     */
    public Terminal getPaquete() {
        return paquete;
    }

    /**
     * Establece el paquete
     *
     * @param paquete Paquete
     */
    public void setPaquete(Terminal paquete) {
        paquete.setPadre(this);
        this.paquete = paquete;
    }

    /**
     * Obtiene el ambito
     *
     * @return Ambito
     */
    public Terminal getAmbito() {
        return ambito;
    }

    /**
     * Establece el ambito
     *
     * @param ambito Ambito
     */
    public void setAmbito(Terminal ambito) {
        ambito.setPadre(this);
        this.ambito = ambito;
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
        return new Simbolo[]{paquete, ambito, identificador, expresion};
    }

}
