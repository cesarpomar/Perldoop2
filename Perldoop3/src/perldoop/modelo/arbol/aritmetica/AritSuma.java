package perldoop.modelo.arbol.aritmetica;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; aritmetica : expresion '+' expresion
 *
 * @author César Pomar
 */
public final class AritSuma extends AritOpBinario{

    /**
     * Único contructor de la clase
     *
     * @param izquierda Simbolo izquierdo
     * @param operador Operador
     * @param derecha Simbolo derecho
     */
    public AritSuma(Expresion izquierda, Terminal operador, Expresion derecha) {
        super(izquierda,operador,derecha);
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
