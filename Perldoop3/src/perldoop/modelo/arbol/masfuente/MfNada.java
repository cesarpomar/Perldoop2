package perldoop.modelo.arbol.masfuente;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; masFuente : 
 *
 * @author César Pomar
 */
public final class MfNada extends MasFuente{

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{};
    }
    
}
