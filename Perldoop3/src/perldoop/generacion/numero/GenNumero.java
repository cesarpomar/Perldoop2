package perldoop.generacion.numero;

import perldoop.modelo.arbol.numero.Decimal;
import perldoop.modelo.arbol.numero.Entero;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de numeros
 *
 * @author César Pomar
 */
public class GenNumero {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenNumero(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Entero s) {

    }

    public void visitar(Decimal s) {

    }

}
