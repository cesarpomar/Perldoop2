package perldoop.modelo.arbol.sentencia;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; sentencia : DECLARACION_TIPO
 *
 * @author César Pomar
 */
public final class StcDeclaracion extends Sentencia {

    private Terminal declaracion;

    /**
     * Único contructor de la clase
     *
     * @param declaracion Declaración
     */
    public StcDeclaracion(Terminal declaracion) {
        setDeclaracion(declaracion);
    }

    /**
     * Obtiene la declaración
     *
     * @return Declaración
     */
    public Terminal getDeclaracion() {
        return declaracion;
    }

    /**
     * Establece la declaración
     *
     * @param declaracion Declaración
     */
    public void setDeclaracion(Terminal declaracion) {
        declaracion.setPadre(this);
        this.declaracion = declaracion;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{declaracion};
    }

}
