package perldoop.semantica.lista;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.acceso.*;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.coleccion.ColLlave;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.util.Tipos;
import perldoop.util.Buscar;

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
