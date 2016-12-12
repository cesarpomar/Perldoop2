package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.cuerpo.Cuerpo;

/**
 * Clase que representa la reduccion -&gt;<br> bloque : '{' cuerpo '}'
 *
 * @author César Pomar
 */
public final class BloqueSimple extends Bloque {

    /**
     * Único contructor de la clase
     *
     * @param contextoBloque Contexto Bloque
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     */
    public BloqueSimple(AbrirBloque contextoBloque, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD) {
        super(contextoBloque, llaveI, cuerpo, llaveD);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{contextoBloque, llaveI, cuerpo, llaveD};
    }
}
