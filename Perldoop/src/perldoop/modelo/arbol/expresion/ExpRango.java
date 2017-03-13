package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.rango.Rango;

/**
 * Clase que representa la reduccion -&gt; expresion : rango
 *
 * @author César Pomar
 */
public final class ExpRango extends Expresion {

    private Rango rango;

    /**
     * Único contructor de la clase
     *
     * @param rango Rango
     */
    public ExpRango(Rango rango) {
        setRango(rango);
    }

    /**
     * Obtiene el rango
     *
     * @return Rango
     */
    public final Rango getRango() {
        return rango;
    }

    /**
     * Establece el rango
     *
     * @param rango rango
     */
    public final void setRango(Rango rango) {
        rango.setPadre(this);
        this.rango = rango;
    }

    @Override
    public Simbolo getValor() {
        return rango;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{rango};
    }
}
