package perldoop.modelo.arbol.logico;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -> logico : LLNOT expresion
 *
 * @author César Pomar
 */
public final class LogNotBajo extends Logico {

    private Terminal expresion;

    /**
     * Único contructor de la clase
     *
     * @param operador Operador
     * @param expresion Expresión
     */
    public LogNotBajo(Terminal operador, Terminal expresion) {
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
        return new Simbolo[]{operador, expresion};
    }
}
