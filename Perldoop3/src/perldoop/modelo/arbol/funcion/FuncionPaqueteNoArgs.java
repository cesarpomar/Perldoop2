package perldoop.modelo.arbol.funcion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.paquete.Paquetes;

/**
 * Clase que representa la reduccion -> <br>
 * variable : paquete ID<br>
 * | ID<br>
 *
 * @author César Pomar
 */
public final class FuncionPaqueteNoArgs extends Funcion {

    private Paquetes paquetes;

    /**
     * Único contructor de la clase
     *
     * @param paquetes paquetes
     * @param identificador Identificador
     */
    public FuncionPaqueteNoArgs(Paquetes paquetes, Terminal identificador) {
        super(identificador);
        setPaquetes(paquetes);
    }

    /**
     * Obtiene los paquetes
     *
     * @return Paquetes
     */
    public Paquetes getPaquetes() {
        return paquetes;
    }

    /**
     * Establece los paquetes
     *
     * @param paquetes Paquetes
     */
    public void setPaquetes(Paquetes paquetes) {
        paquetes.setPadre(this);
        this.paquetes = paquetes;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{paquetes, identificador};
    }
}
