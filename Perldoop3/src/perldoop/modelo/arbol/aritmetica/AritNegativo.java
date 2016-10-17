package perldoop.modelo.arbol.aritmetica;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; aritmetica : '-' expresion
 *
 * @author César Pomar
 */
public final class AritNegativo extends AritOpUnitario {

    /**
     * Único contructor de la clase
     *
     * @param operador Operador
     * @param expresion Expresión
     */
    public AritNegativo(Terminal operador, Expresion expresion) {
        super(operador, expresion);
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
