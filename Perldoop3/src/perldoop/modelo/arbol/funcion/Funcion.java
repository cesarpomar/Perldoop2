package perldoop.modelo.arbol.funcion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.paquete.Paquete;

/**
 * Clase abtracta que representa todas las reduciones de funcion
 *
 * @author César Pomar
 */
public abstract class Funcion extends Simbolo {

    protected Paquete paquete;
    protected Terminal identificador;

    /**
     * Único contructor de la clase
     *
     * @param paquete Paquete
     * @param identificador Identificador
     */
    public Funcion(Paquete paquete, Terminal identificador) {
        if (paquete != null) {
            setPaquete(paquete);
        }
        setIdentificador(identificador);
    }

    /**
     * Obtiene el paquete
     *
     * @return Paquete
     */
    public final Paquete getPaquete() {
        return paquete;
    }

    /**
     * Establece el paquete
     *
     * @param paquete Paquete
     */
    public final void setPaquete(Paquete paquete) {
        paquete.setPadre(this);
        this.paquete = paquete;
    }

    /**
     * Obtiene el identificador
     *
     * @return Identificador
     */
    public final Terminal getIdentificador() {
        return identificador;
    }

    /**
     * Establece el identificador
     *
     * @param identificador Identificador
     */
    public final void setIdentificador(Terminal identificador) {
        identificador.setPadre(this);
        this.identificador = identificador;
    }

}
