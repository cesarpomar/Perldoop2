package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.cuerpo.Cuerpo;

/**
 * Clase abtracta que representa todas las reduciones de bloque
 *
 * @author César Pomar
 */
public abstract class Bloque extends Simbolo {

    protected Terminal llaveI;
    protected Cuerpo cuerpo;
    protected Terminal llaveD;

    /**
     * Único contructor de la clase
     *

     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     */
    public Bloque(Terminal llaveI, Cuerpo cuerpo, Terminal llaveD) {
        setLlaveI(llaveI);
        setCuerpo(cuerpo);
        setLlaveD(llaveD);
    }

    /**
     * Obtiene la llave izquierda
     *
     * @return Llave izquierda
     */
    public final Terminal getLlaveI() {
        return llaveI;
    }

    /**
     * Establece la llave izquierda
     *
     * @param llaveI Llave izquierda
     */
    public final void setLlaveI(Terminal llaveI) {
        llaveI.setPadre(this);
        this.llaveI = llaveI;
    }

    /**
     * Obtiene el cuerpo
     *
     * @return Cuerpo
     */
    public final Cuerpo getCuerpo() {
        return cuerpo;
    }

    /**
     * Establece el cuerpo
     *
     * @param cuerpo Cuerpo
     */
    public final void setCuerpo(Cuerpo cuerpo) {
        cuerpo.setPadre(this);
        this.cuerpo = cuerpo;
    }

    /**
     * Obtiene la llave derecha
     *
     * @return Llave derecha
     */
    public final Terminal getLlaveD() {
        return llaveD;
    }

    /**
     * Establece la llave derecha
     *
     * @param llaveD Llave derecha
     */
    public final void setLlaveD(Terminal llaveD) {
        llaveD.setPadre(this);
        this.llaveD = llaveD;
    }

}
