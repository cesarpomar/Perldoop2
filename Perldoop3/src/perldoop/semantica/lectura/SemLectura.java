package perldoop.semantica.lectura;

import perldoop.modelo.arbol.lectura.LecturaFile;
import perldoop.modelo.arbol.lectura.LecturaArg;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de lectura
 *
 * @author César Pomar
 */
public class SemLectura {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemLectura(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(LecturaArg s) {
    }

    public void visitar(LecturaFile s) {
    }

}
