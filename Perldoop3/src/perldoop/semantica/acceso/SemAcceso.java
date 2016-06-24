package perldoop.semantica.acceso;

import perldoop.modelo.arbol.acceso.*;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de acceso
 *
 * @author César Pomar
 */
public class SemAcceso {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemAcceso(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(AccesoMap s) {
    }

    public void visitar(AccesoArray s) {
    }

    public void visitar(AccesoMapRef s) {
    }

    public void visitar(AccesoArrayRef s) {
    }

    public void visitar(AccesoRefEscalar s) {
    }

    public void visitar(AccesoRefArray s) {
    }

    public void visitar(AccesoRefMap s) {
    }

    public void visitar(AccesoSigil s) {
    }
}
