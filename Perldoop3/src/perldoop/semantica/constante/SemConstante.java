package perldoop.semantica.constante;

import perldoop.modelo.arbol.constante.CadenaComando;
import perldoop.modelo.arbol.constante.CadenaDoble;
import perldoop.modelo.arbol.constante.CadenaSimple;
import perldoop.modelo.arbol.constante.Decimal;
import perldoop.modelo.arbol.constante.Entero;
import perldoop.modelo.semantica.Tipo;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de constante
 *
 * @author César Pomar
 */
public class SemConstante {

    /*Tabla de simbolos */
    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemConstante(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(Entero s) {
        s.setTipo(new Tipo(Tipo.INTEGER));
    }

    public void visitar(Decimal s) {
        s.setTipo(new Tipo(Tipo.INTEGER));
    }

    public void visitar(CadenaSimple s) {
        s.setTipo(new Tipo(Tipo.INTEGER));
    }

    public void visitar(CadenaDoble s) {
        s.setTipo(new Tipo(Tipo.INTEGER));
    }

    public void visitar(CadenaComando s) {
        s.setTipo(new Tipo(Tipo.INTEGER));
    }

}
