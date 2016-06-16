package perldoop.modelo.arbol.funcion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.paquete.Paquete;

/**
 * Clase que representa la reduccion -> <br>
 * variable : paquete ID expresion<br>
 * | ID expresion<br>
 *
 * @author César Pomar
 */
public final class FuncionArgs extends Funcion {

    private Expresion expresion;

    /**
     * Único contructor de la clase
     *
     * @param paquete Paquete
     * @param identificador Identificador
     * @param expresion Expresión
     */
    public FuncionArgs(Paquete paquete, Terminal identificador, Expresion expresion) {
        super(paquete, identificador);
        setExpresion(expresion);
    }

    /**
     * Obtiene la expresión
     *
     * @return Expresión
     */
    public Expresion getExpresion() {
        return expresion;
    }

    /**
     * Establece la expresión
     *
     * @param expresion Expresión
     */
    public void setExpresion(Expresion expresion) {
        expresion.setPadre(this);
        this.expresion = expresion;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        if (paquete == null) {
            return new Simbolo[]{identificador, expresion};
        } else {
            return new Simbolo[]{paquete, identificador, expresion};
        }
    }

}
