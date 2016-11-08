package perldoop.semantica.cadenatexto;

import perldoop.modelo.arbol.cadenatexto.CadenaTexto;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de cadenaTexto
 *
 * @author César Pomar
 */
public class SemCadenaTexto {

    /*Tabla de simbolos */
    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemCadenaTexto(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(CadenaTexto s) {
        //No es necesario hacer nada
    }

}
