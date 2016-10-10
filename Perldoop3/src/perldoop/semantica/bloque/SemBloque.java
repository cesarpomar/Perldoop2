package perldoop.semantica.bloque;

import perldoop.modelo.arbol.bloque.*;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de bloque
 *
 * @author César Pomar
 */
public class SemBloque {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemBloque(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(BloqueWhile s) {
        tabla.getTablaSimbolos().cerrarBloque();
    }

    public void visitar(BloqueUntil s) {
        tabla.getTablaSimbolos().cerrarBloque();
    }

    public void visitar(BloqueDoWhile s) {
        tabla.getTablaSimbolos().cerrarBloque();
    }

    public void visitar(BloqueDoUntil s) {
        tabla.getTablaSimbolos().cerrarBloque();
    }

    public void visitar(BloqueFor s) {
        tabla.getTablaSimbolos().cerrarBloque();
    }

    public void visitar(BloqueForeachVar s) {
        tabla.getTablaSimbolos().cerrarBloque();
    }

    public void visitar(BloqueForeach s) {
        tabla.getTablaSimbolos().cerrarBloque();
    }

    public void visitar(BloqueIf s) {
        tabla.getTablaSimbolos().cerrarBloque();
    }

    public void visitar(BloqueUnless s) {
        tabla.getTablaSimbolos().cerrarBloque();
    }

    public void visitar(BloqueVacio s) {
        //No tiene cabecera
    }
}
