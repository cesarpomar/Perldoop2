package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.acceso.Acceso;

/**
 * Clase que representa la reduccion -> expresion : acceso
 *
 * @author César Pomar
 */
public final class ExpAcceso extends Expresion {

    private Acceso acceso;

    /**
     * Único contructor de la clase
     * @param acceso Acceso
     */
    public ExpAcceso(Acceso acceso) {
        setAcceso(acceso);
    }

    /**
     * obtiene el acceso
     * @return Acceso
     */
    public Acceso getAcceso() {
        return acceso;
    }

    /**
     * Establece el acceso
     * @param acceso Acceso
     */
    public void setAcceso(Acceso acceso) {
        acceso.setPadre(this);
        this.acceso = acceso;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{acceso};
    }

}
