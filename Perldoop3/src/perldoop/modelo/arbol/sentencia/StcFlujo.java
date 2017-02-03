package perldoop.modelo.arbol.sentencia;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.flujo.Flujo;
import perldoop.modelo.arbol.modificador.Modificador;

/**
 * Clase que representa la reduccion -&gt; sentencia : flujo modificador ';'
 *
 * @author César Pomar
 */
public final class StcFlujo extends Sentencia {

    private Flujo flujo;
    private Modificador modificador;
    private Terminal puntoComa;

    /**
     * Único contructor de la clase
     *
     * @param flujo Flujo
     * @param modificador Modificador
     * @param puntoComa PuntoComa
     */
    public StcFlujo(Flujo flujo, Modificador modificador, Terminal puntoComa) {
        setFlujo(flujo);
        setModificador(modificador);
        setPuntoComa(puntoComa);
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

    /**
     * Obtiene el modificador
     *
     * @return Modificador
     */
    public Modificador getModificador() {
        return modificador;
    }

    /**
     * Establece el modificador
     *
     * @param modificador Modificador
     */
    public void setModificador(Modificador modificador) {
        modificador.setPadre(this);
        this.modificador = modificador;
    }

    /**
     * Obtiene el punto y coma ';'
     *
     * @return PuntoComa
     */
    public Terminal getPuntoComa() {
        return puntoComa;
    }

    /**
     * Establece el punto y coma ';'
     *
     * @param puntoComa PuntoComa
     */
    public void setPuntoComa(Terminal puntoComa) {
        puntoComa.setPadre(this);
        this.puntoComa = puntoComa;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{flujo, modificador, puntoComa};
    }

}
