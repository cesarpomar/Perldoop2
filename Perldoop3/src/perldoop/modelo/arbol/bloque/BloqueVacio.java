package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.contexto.Contexto;

/**
 * Clase que representa la reduccion -&gt;<br> bloque : '{' cuerpo '}'
 *
 * @author César Pomar
 */
public final class BloqueVacio extends Bloque {

    /**
     * Único contructor de la clase
     *
     * @param contexto Contexto del bloque
     */
    public BloqueVacio(Contexto contexto) {
        super(contexto);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{contexto};
    }
}
