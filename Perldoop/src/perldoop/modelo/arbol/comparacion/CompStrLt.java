package perldoop.modelo.arbol.comparacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; compracion : expresion STR_LT expresion
 *
 * @author César Pomar
 */
public final class CompStrLt extends Comparacion {

    /**
     * Único contructor de la clase
     *
     * @param izquierda Simbolo izquierdo
     * @param operador Operador
     * @param derecha Simbolo derecho
     */
    public CompStrLt(Expresion izquierda, Terminal operador, Expresion derecha) {
        super(izquierda, operador, derecha);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{izquierda, operador, derecha};
    }
}
