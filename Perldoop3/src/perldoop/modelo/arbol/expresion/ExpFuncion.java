package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.funcion.Funcion;

/**
 * Clase que representa la reduccion -&gt; expresion : funcion
 *
 * @author César Pomar
 */
public final class ExpFuncion extends Expresion {

    private Funcion funcion;

    /**
     * Único contructor de la clase
     *
     * @param funcion Función
     */
    public ExpFuncion(Funcion funcion) {
        setFuncion(funcion);
    }

    /**
     * Obtiene la función
     *
     * @return Función
     */
    public Funcion getFuncion() {
        return funcion;
    }

    /**
     * Establece la función
     *
     * @param funcion Función
     */
    public void setFuncion(Funcion funcion) {
        funcion.setPadre(this);
        this.funcion = funcion;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{funcion};
    }

}
