package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.coleccion.Coleccion;

/**
 * Clase que representa la reduccion -&gt; expresion : coleccion
 *
 * @author César Pomar
 */
public final class ExpColeccion extends Expresion {

    private Coleccion coleccion;

    /**
     * Único contructor de la clase
     *
     * @param coleccion Colección
     */
    public ExpColeccion(Coleccion coleccion) {
        setColeccion(coleccion);
    }

    /**
     * Obtiene la colección
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
    public Simbolo getValor() {
        return coleccion;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{coleccion};
    }

}
