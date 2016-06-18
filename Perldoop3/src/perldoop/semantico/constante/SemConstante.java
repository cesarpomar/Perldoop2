package perldoop.semantico.constante;

import perldoop.modelo.arbol.constante.CadenaComando;
import perldoop.modelo.arbol.constante.CadenaDoble;
import perldoop.modelo.arbol.constante.CadenaSimple;
import perldoop.modelo.arbol.constante.Decimal;
import perldoop.modelo.arbol.constante.Entero;
import perldoop.modelo.semantico.TablaSimbolos;
import perldoop.modelo.semantico.Tipo;

/**
 * Clase para la semantica de las constantes
 *
 * @author César Pomar
 */
public class SemConstante {

    /*Tabla de simbolos */
    private TablaSimbolos ts;

    /**
     * Contruye la semantica
     *
     * @param ts Tabla de simbolos
     */
    public SemConstante(TablaSimbolos ts) {
        this.ts = ts;
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
