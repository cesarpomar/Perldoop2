package perldoop.semantica.cadena;

import perldoop.modelo.arbol.cadena.*;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;

/**
 * Clase para la semantica de cadenas
 *
 * @author César Pomar
 */
public class SemCadena {

    /*Tabla de simbolos */
    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemCadena(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(CadenaSimple s) {
        s.setTipo(new Tipo(Tipo.STRING));
    }

    public void visitar(CadenaDoble s) {
        s.setTipo(new Tipo(Tipo.STRING));
    }

    public void visitar(CadenaComando s) {
        s.setTipo(new Tipo(Tipo.STRING));
    }

    public void visitar(CadenaQ s) {
        s.setTipo(new Tipo(Tipo.STRING));
    }

    public void visitar(CadenaQW s) {
        s.setTipo(new Tipo(Tipo.ARRAY,Tipo.STRING));
    }

    public void visitar(CadenaQQ s) {
        s.setTipo(new Tipo(Tipo.STRING));
    }

    public void visitar(CadenaQX s) {
        s.setTipo(new Tipo(Tipo.STRING));
    }

    public void visitar(CadenaQR s) {
        s.setTipo(new Tipo(Tipo.STRING));
    }

}
