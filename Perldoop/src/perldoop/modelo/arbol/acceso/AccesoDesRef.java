package perldoop.modelo.arbol.acceso;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; acceso : '$' expresion | '@' expresion | '%' expresion
 *
 * @author César Pomar
 */
public final class AccesoDesRef extends Acceso {

    private Terminal contexto;

    /**
     * Único contructor de la clase
     *
     * @param contexto Contexto
     * @param expresion Expresión
     */
    public AccesoDesRef(Terminal contexto, Expresion expresion) {
        super(expresion);
        setContexto(contexto);
    }

    /**
     * Obtiene la contexto
     *
     * @return Contexto
     */
    public Terminal getContexto() {
        return contexto;
    }

    /**
     * Establece la contexto
     *
     * @param contexto Contexto
     */
    public void setContexto(Terminal contexto) {
        contexto.setPadre(padre);
        this.contexto = contexto;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{contexto, expresion};
    }
}
