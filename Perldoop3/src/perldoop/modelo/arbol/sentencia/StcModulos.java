package perldoop.modelo.arbol.sentencia;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.modulos.Modulo;

/**
 * Clase que representa la reduccion -&gt; expresion : modulos
 *
 * @author César Pomar
 */
public final class StcModulos extends Sentencia {

    private Modulo modulo;
    private Terminal puntoComa;

    /**
     * Único contructor de la clase
     *
     * @param modulo Modulo
     * @param puntoComa PuntoComa
     */
    public StcModulos(Modulo modulo, Terminal puntoComa) {
        setModulos(modulo);
        setPuntoComa(puntoComa);
    }

    /**
     * Obtiene la modulo
     *
     * @return Modulo
     */
    public Modulo getModulos() {
        return modulo;
    }

    /**
     * Establece la modulo
     *
     * @param modulo Modulo
     */
    public void setModulos(Modulo modulo) {
        modulo.setPadre(this);
        this.modulo = modulo;
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
        return new Simbolo[]{modulo, puntoComa};
    }
}
