package perldoop.modelo.arbol.funcion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.paquete.Paquetes;

/**
 * Clase que representa la reduccion -&gt; funcion : Paquete ID expresion
 *
 * @author César Pomar
 */
public final class FuncionBasica extends Funcion {

    /**
     * Único contructor de la clase
     *
     * @param paquetes Paquetes
     * @param identificador Identificador
     * @param coleccion Colección
     */
    public FuncionBasica(Paquetes paquetes, Terminal identificador, ColParentesis coleccion) {
        super(paquetes, identificador, coleccion);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{paquetes, identificador, coleccion};
    }

}
