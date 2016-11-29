package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.cuerpo.Cuerpo;

/**
 * Clase abtracta que representa todas las reduciones de bloques dependientes de un bloque if o unless
 *
 * @author César Pomar
 */
public abstract class SubBloque extends Bloque {

    /**
     * Contructor por defecto de la clase
     *
     * @param contextoBloque Contexto bloque
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     */
    public SubBloque(AbrirBloque contextoBloque, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD) {
        super(contextoBloque, llaveI, cuerpo, llaveD);
    }

    /**
     * Contructor de bloque vacio
     */
    public SubBloque() {
    }

}
