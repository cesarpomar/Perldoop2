package perldoop.modelo.arbol.cadena;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.cadenatexto.CadenaTexto;

/**
 * Clase abtracta que representa todas las reduciones de cadenas de texto
 *
 * @author César Pomar
 */
public abstract class Cadena extends Simbolo {

    protected Terminal sepI;
    protected CadenaTexto texto;
    protected Terminal sepD;

    /**
     * Constructor unico de la clase
     *
     * @param sepI Separador izquierdo
     * @param texto Texto
     * @param sepD Separador derecho
     */
    public Cadena(Terminal sepI, CadenaTexto texto, Terminal sepD) {
        setSepI(sepI);
        setTexto(texto);
        setSepD(sepD);
    }

    /**
     * Obtiene el separador izquierdo
     *
     * @return Separador izquierdo
     */
    public final Terminal getSepI() {
        return sepI;
    }

    /**
     * Establece el separador izquierdo
     *
     * @param sepI Separador izquierdo
     */
    public final void setSepI(Terminal sepI) {
        sepI.setPadre(this);
        this.sepI = sepI;
    }

    /**
     * Obtiene el texto
     *
     * @return Texto
     */
    public final CadenaTexto getTexto() {
        return texto;
    }

    /**
     * Establece el texto
     *
     * @param texto Texto
     */
    public final void setTexto(CadenaTexto texto) {
        texto.setPadre(this);
        this.texto = texto;
    }

    /**
     * Obtiene el separador derecho
     *
     * @return Separador derecho
     */
    public final Terminal getSepD() {
        return sepD;
    }

    /**
     * Establece el separador derecho
     *
     * @param sepD Separador derecho
     */
    public final void setSepD(Terminal sepD) {
        sepD.setPadre(this);
        this.sepD = sepD;
    }

}
