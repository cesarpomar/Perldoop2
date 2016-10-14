package perldoop.modelo.arbol.sentencia;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.modulos.Modulo;

/**
 * Clase que representa la reduccion -&gt; expresion : modulos
 *
 * @author César Pomar
 */
public class StcModulos extends Sentencia {

    private Modulo modulo;

    /**
     * Único contructor de la clase
     *
     * @param modulo Modulo
     */
    public StcModulos(Modulo modulo) {
        setModulos(modulo);
    }

    /**
     * Obtiene la modulo
     *
     * @return Modulo
     */
    public final Modulo getModulos() {
        return modulo;
    }

    /**
     * Establece la modulo
     *
     * @param modulo Modulo
     */
    public final void setModulos(Modulo modulo) {
        modulo.setPadre(this);
        this.modulo = modulo;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{modulo};
    }
}
