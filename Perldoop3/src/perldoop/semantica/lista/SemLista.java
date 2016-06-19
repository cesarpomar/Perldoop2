package perldoop.semantica.lista;

import perldoop.modelo.arbol.lista.Lista;
import perldoop.semantica.TablaSemantica;

/**
 * Clase para la semantica de lista
 *
 * @author César Pomar
 */
public class SemLista {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemLista(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(Lista s) {
    }
}
