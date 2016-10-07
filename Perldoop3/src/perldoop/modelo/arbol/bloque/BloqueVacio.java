package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.cuerpo.Cuerpo;

/**
 * Clase que representa la reduccion -&gt;<br> bloque : '{' cuerpo '}'
 *
 * @author César Pomar
 */
public final class BloqueVacio extends Bloque {

    /**
     * Único contructor de la clase
     *
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     */
    public BloqueVacio(Terminal llaveI, Cuerpo cuerpo, Terminal llaveD) {
        super(llaveI, cuerpo, llaveD);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{llaveI, cuerpo, llaveD};
    }
}
