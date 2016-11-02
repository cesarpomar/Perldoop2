package perldoop.semantica.rango;

import perldoop.modelo.arbol.rango.Rango;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de raiz
 *
 * @author César Pomar
 */
public final class SemRango {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemRango(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(Rango s) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }



}
