package perldoop.modelo.arbol.aritmetica;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -> aritmetica : expresion MENOS_MENOS
 *
 * @author César Pomar
 */
public final class AritPostDecremento extends Aritmetica {

    private Terminal expresion;

    /**
     * Único contructor de la clase
     *
     * @param expresion Expresión
     * @param operador Operador
     */
    public AritPostDecremento(Terminal expresion, Terminal operador) {
        super(operador);
        setExpresion(expresion);
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

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{expresion, operador};
    }
}
