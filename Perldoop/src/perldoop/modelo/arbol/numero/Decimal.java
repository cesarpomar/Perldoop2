package perldoop.modelo.arbol.numero;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; numero : DECIMAL
 *
 * @author César Pomar
 */
public final class Decimal extends Numero {

    /**
     * Unico contructor de la clase
     *
     * @param numero Numero
     */
    public Decimal(Terminal numero) {
        super(numero);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{numero};
    }

}
