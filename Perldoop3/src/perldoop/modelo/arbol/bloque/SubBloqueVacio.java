package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.cuerpo.Cuerpo;

/**
 * Clase que representa la reduccion -&gt;<br> condicional :
 *
 * @author César Pomar
 */
public final class SubBloqueVacio extends SubBloque {

    /**
     * Único contructor de la clase
     */
    public SubBloqueVacio() {
        super(null, null, null);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{};
    }

}
