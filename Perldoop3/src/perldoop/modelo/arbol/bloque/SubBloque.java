package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.cuerpo.Cuerpo;

/**
 * Clase abtracta que representa todas las reduciones de bloques dependientes de un bloque if o unless
 *
 * @author César Pomar
 */
public abstract class SubBloque extends Bloque {

    /**
     * Único contructor de la clase
     *
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     */
    public SubBloque(Terminal llaveI, Cuerpo cuerpo, Terminal llaveD) {
        super(llaveI, cuerpo, llaveD);
    }

}
