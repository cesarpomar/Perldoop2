package perldoop.modelo.arbol.sentencia;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.flujo.Flujo;

/**
 * Clase que representa la reduccion -> sentencia : flujo
 *
 * @author César Pomar
 */
public final class StcFlujo extends Sentencia {

    private Flujo flujo;

    /**
     * Único contructor de la clase
     *
     * @param flujo Flujo
     */
    public StcFlujo(Flujo flujo) {
        setFlujo(flujo);
    }

    /**
     * Obtiene el flujo
     *
     * @return Flujo
     */
    public Flujo getFlujo() {
        return flujo;
    }

    /**
     * Establece el Flujo
     *
     * @param flujo Flujo
     */
    public void setFlujo(Flujo flujo) {
        flujo.setPadre(this);
        this.flujo = flujo;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{flujo};
    }

}
