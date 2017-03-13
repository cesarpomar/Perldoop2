package perldoop.modelo.arbol.asignacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; asignacion : expresion DLOR_IGUAL expresion
 *
 * @author César Pomar
 */
public final class DLOrIgual extends Asignacion {

    /**
     * Único contructor de la clase
     *
     * @param izquierda Izquierda
     * @param operador Operador
     * @param derecha Derecha
     */
    public DLOrIgual(Expresion izquierda, Terminal operador, Expresion derecha) {
        super(izquierda, operador, derecha);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{izquierda, operador, derecha};
    }

}