package perldoop.modelo.arbol.funcion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.paquete.Paquete;

/**
 * Clase que representa la reduccion -> <br>
 * variable : paquete ID<br>
 * | ID<br>
 *
 * @author César Pomar
 */
public final class FuncionNoArgs extends Funcion {

    /**
     * Único contructor de la clase
     *
     * @param paquete Paquete
     * @param identificador Identificador
     */
    public FuncionNoArgs(Paquete paquete, Terminal identificador) {
        super(paquete, identificador);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        if (paquete == null) {
            return new Simbolo[]{identificador};

        } else {
            return new Simbolo[]{paquete, identificador};
        }
    }
}
