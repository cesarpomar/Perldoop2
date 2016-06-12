package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.asignacion.Asignacion;

/**
 * Clase que representa la reduccion -> expresion : asignacion
 *
 * @author César Pomar
 */
public final class ExpAsignacion extends Expresion {

    private Asignacion asignacion;

    /**
     * Único contructor de la clase
     *
     * @param asignacion
     */
    public ExpAsignacion(Asignacion asignacion) {
        setAsignacion(asignacion);
    }

    /**
     * Obtiene la asignación
     * @return Asignación
     */
    public Asignacion getAsignacion() {
        return asignacion;
    }

    /**
     * Establece la asignación
     * @param asignacion Asignación
     */
    public void setAsignacion(Asignacion asignacion) {
        asignacion.setPadre(this);
        this.asignacion = asignacion;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{asignacion};
    }
}
