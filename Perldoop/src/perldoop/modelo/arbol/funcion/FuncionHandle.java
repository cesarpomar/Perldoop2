package perldoop.modelo.arbol.funcion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.handle.Handle;
import perldoop.modelo.arbol.paquete.Paquetes;

/**
 * Clase que representa la reduccion -&gt; funcion : ID handle expresion | ID_P '(' handle expresion ')'
 * 
 *
 * @author César Pomar
 */
public final class FuncionHandle extends Funcion {

    protected Handle handle;

    /**
     * Único contructor de la clase
     *
     * @param paquetes Paquetes
     * @param identificador Identificador
     * @param handle Handle
     * @param coleccion Colección
     */
    public FuncionHandle(Paquetes paquetes, Terminal identificador, Handle handle, ColParentesis coleccion) {
        super(paquetes, identificador, coleccion);
        setHandle(handle);
    }

    /**
     * Obtiene el handle
     *
     * @return Handle
     */
    public Handle getHandle() {
        return handle;
    }

    /**
     * Establece el handle
     *
     * @param handle Handle
     */
    public void setHandle(Handle handle) {
        handle.setPadre(this);
        this.handle = handle;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{paquetes, identificador, handle, coleccion};
    }

}
