package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.std.Std;

/**
 * Clase que representa la reduccion -&gt; expresion : std
 *
 * @author César Pomar
 */
public final class ExpStd extends Expresion {

    private Std std;

    /**
     * Único contructor de la clase
     *
     * @param std Std
     */
    public ExpStd(Std std) {
        setStd(std);
    }

    /**
     * Obtiene la std
     *
     * @return Std
     */
    public final Std getStd() {
        return std;
    }

    /**
     * Establece la std
     *
     * @param std Std
     */
    public final void setStd(Std std) {
        std.setPadre(this);
        this.std = std;
    }

    @Override
    public Simbolo getValor() {
        return std;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{std};
    }
}
