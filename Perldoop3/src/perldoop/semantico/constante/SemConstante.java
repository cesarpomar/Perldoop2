package perldoop.semantico.constante;

import perldoop.modelo.arbol.constante.CadenaComando;
import perldoop.modelo.arbol.constante.CadenaDoble;
import perldoop.modelo.arbol.constante.CadenaSimple;
import perldoop.modelo.arbol.constante.Decimal;
import perldoop.modelo.arbol.constante.Entero;
import perldoop.modelo.semantico.Tipo;

/**
 *
 * @author César
 */
public class SemConstante {

    public void visitar(CadenaComando s) {
        s.setTipo(new Tipo(Tipo.STRING));
    }

    public void visitar(CadenaDoble s) {
        s.setTipo(new Tipo(Tipo.STRING));
    }

    public void visitar(CadenaSimple s) {
        s.setTipo(new Tipo(Tipo.STRING));
    }

    public void visitar(Decimal s) {
        s.setTipo(new Tipo(Tipo.DOUBLE));
    }

    public void visitar(Entero s) {
        s.setTipo(new Tipo(Tipo.INTEGER));
    }
    
}
