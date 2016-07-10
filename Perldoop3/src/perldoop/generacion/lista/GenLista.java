package perldoop.generacion.lista;

import java.util.Iterator;
import perldoop.generacion.util.Casting;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.semantica.Tipo;

/**
 * Clase generadora de lista
 *
 * @author César Pomar
 */
public class GenLista {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenLista(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Lista s) {
    }

}
