package perldoop.modelo.arbol.funcion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.paquete.Paquetes;

/**
 * Clase abtracta que representa todas las reduciones de funcion
 *
 * @author César Pomar
 */
public abstract class Funcion extends Simbolo {

    protected Paquetes paquetes;
    protected Terminal identificador;
    protected ColParentesis coleccion;

    /**
     * Único contructor de la clase
     *
     * @param paquetes Paquetes
     * @param identificador Identificador
     * @param coleccion Colección
     */
    public Funcion(Paquetes paquetes, Terminal identificador, ColParentesis coleccion) {
        setPaquetes(paquetes);
        setIdentificador(identificador);
        setColeccion(coleccion);
    }

    /**
     * Obtiene los paquetes
     *
     * @return Paquetes
     */
    public final Paquetes getPaquetes() {
        return paquetes;
    }

    /**
     * Establece los paquetes
     *
     * @param paquetes Paquetes
     */
    public final void setPaquetes(Paquetes paquetes) {
        paquetes.setPadre(this);
        this.paquetes = paquetes;
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

    /**
     * Obtiene la colección
     *
     * @return Colección
     */
    public final ColParentesis getColeccion() {
        return coleccion;
    }

    /**
     * Establece la colección
     *
     * @param coleccion Colección
     */
    public final void setColeccion(ColParentesis coleccion) {
        coleccion.setPadre(this);
        this.coleccion = coleccion;
    }

}
