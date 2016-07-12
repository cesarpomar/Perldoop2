package perldoop.modelo.arbol.acceso;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -> acceso : expresion coleccion
 *
 * @author César Pomar
 */
public final class AccesoCol extends Acceso {

    private Coleccion coleccion;

    /**
     * Único contructor de la clase
     *
     * @param expresion Expresión
     * @param coleccion Colección
     */
    public AccesoCol(Expresion expresion, Coleccion coleccion) {
        super(expresion);
        setColeccion(coleccion);
    }

    /**
     * Obtiene la coleccíon
     *
     * @return Colección
     */
    public Coleccion getColeccion() {
        return coleccion;
    }

    /**
     * Establece la colección
     *
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
        return new Simbolo[]{expresion, coleccion};
    }

}
