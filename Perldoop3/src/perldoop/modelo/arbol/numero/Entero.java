package perldoop.modelo.arbol.numero;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; numero : ENTERO
 *
 * @author César Pomar
 */
public final class Entero extends Numero {

    /**
     * Unico contructor de la clase
     *
     * @param numero Numero
     */
    public Entero(Terminal numero) {
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
