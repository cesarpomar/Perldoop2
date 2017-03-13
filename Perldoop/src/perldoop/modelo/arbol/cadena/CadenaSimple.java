package perldoop.modelo.arbol.cadena;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.cadenatexto.CadenaTexto;

/**
 * Clase que representa la reduccion -&gt; cadena : '\'' cadenaTexto '\''
 *
 * @author César Pomar
 */
public final class CadenaSimple extends Cadena {

    /**
     * Constructor unico de la clase
     *
     * @param sepI Separador izquierdo
     * @param texto Texto
     * @param sepD Separador derecho
     */
    public CadenaSimple(Terminal sepI, CadenaTexto texto, Terminal sepD) {
        super(sepI, texto, sepD);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{sepI, texto, sepD};
    }

}
