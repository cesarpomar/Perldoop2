package perldoop.modelo.arbol.acceso;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -> acceso : expresion FLECHA coleccion
 *
 * @author César Pomar
 */
public final class AccesoColRef extends Acceso {

    private Terminal flecha;
    private Coleccion coleccion;

    /**
     * Único contructor de la clase
     *
     * @param expresion Expresión
     * @param flecha Flecha
     * @param coleccion Colección
     */
    public AccesoColRef(Expresion expresion, Terminal flecha, Coleccion coleccion) {
        super(expresion);
        setFlecha(flecha);
        setColeccion(coleccion);
    }

    /**
     * Obtiene la flecha
     *
     * @return Flecha
     */
    public Terminal getFlecha() {
        return flecha;
    }

    /**
     * Establece la flecha
     *
     * @param flecha Flecha
     */
    public void setFlecha(Terminal flecha) {
        flecha.setPadre(this);
        this.flecha = flecha;
    }

    /**
     * Obtiene la coleccíon
     * @return Colección
     */
    public Coleccion getColeccion() {
        return coleccion;
    }

    /**
     * Establece la colección
     * @param coleccion Colección
     */
    public void setColeccion(Coleccion coleccion) {
        coleccion.setPadre(this);
        this.coleccion = coleccion;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{expresion, flecha, coleccion};
    }
}
