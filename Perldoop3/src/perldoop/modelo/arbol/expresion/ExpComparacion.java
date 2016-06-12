package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.comparacion.Comparacion;

/**
 * Clase que representa la reduccion -> expresion : comparacion
 *
 * @author César Pomar
 */
public final class ExpComparacion extends Simbolo {

    private Comparacion comparacion;

    /**
     * Único contructor de la clase
     *
     * @param comparacion
     */
    public ExpComparacion(Comparacion comparacion) {
        setComparacion(comparacion);
    }

    /**
     * Obtiene la comparación
     *
     * @return Comparación
     */
    public Comparacion getComparacion() {
        return comparacion;
    }

    /**
     * Establece la comparación
     *
     * @param comparacion Comparación
     */
    public void setComparacion(Comparacion comparacion) {
        comparacion.setPadre(this);
        this.comparacion = comparacion;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{comparacion};
    }

}
