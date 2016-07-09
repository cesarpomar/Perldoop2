package perldoop.semantica.lista;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.acceso.Acceso;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.semantica.TablaSemantica;

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
        //No hay que nada que comprobar
    }
    
    
}
