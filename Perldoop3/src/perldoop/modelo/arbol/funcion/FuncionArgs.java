package perldoop.modelo.arbol.funcion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.ExpColeccion;

/**
 * Clase que representa la reduccion -&gt; funcion : ID expresion
 *
 * @author César Pomar
 */
public final class FuncionArgs extends Funcion {

    private ExpColeccion coleccion;

    /**
     * Único contructor de la clase
     *
     * @param coleccion Colección
     * @param identificador Identificador
     */
    public FuncionArgs(Terminal identificador, ExpColeccion coleccion) {
        super(identificador);
        setColeccion(coleccion);
    }

    /**
     * Obtiene la colección
     *
     * @return Colección
     */
    public ExpColeccion getColeccion() {
        return coleccion;
    }

    /**
     * Establece la colección
     *
     * @param coleccion Colección
     */
    public void setColeccion(ExpColeccion coleccion) {
        coleccion.setPadre(this);
        this.coleccion = coleccion;
    }


    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{identificador, coleccion};
    }

}
