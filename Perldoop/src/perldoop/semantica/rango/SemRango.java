package perldoop.semantica.rango;

import perldoop.modelo.arbol.rango.Rango;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;

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
        if (s.getIzquierda().getTipo().isInteger() && s.getDerecha().getTipo().isInteger()) {
            s.setTipo(new Tipo(Tipo.ARRAY, Tipo.INTEGER));
        } else {
            s.setTipo(new Tipo(Tipo.ARRAY, Tipo.STRING));
        }
    }

}
