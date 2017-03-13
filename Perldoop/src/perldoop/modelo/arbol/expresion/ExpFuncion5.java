package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.funcion.Funcion;

/**
 * Clase que representa la reduccion -&gt; expresion : funcion
 *
 * @author César Pomar
 */
public final class ExpFuncion5 extends Expresion {

    private Terminal ampersand;
    private Funcion funcion;

    /**
     * Único contructor de la clase
     *
     * @param ampersand Ampersand
     * @param funcion Función
     */
    public ExpFuncion5(Terminal ampersand, Funcion funcion) {
        setAmpersand(ampersand);
        setFuncion(funcion);
    }

    /**
     * Obtiene el ampersand
     *
     * @return Ampersand
     */
    public Terminal getAmpersand() {
        return ampersand;
    }

    /**
     * Establece el ampersand
     *
     * @param ampersand Ampersand
     */
    public void setAmpersand(Terminal ampersand) {
        this.ampersand = ampersand;
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
    public Simbolo getValor() {
        return funcion;
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
