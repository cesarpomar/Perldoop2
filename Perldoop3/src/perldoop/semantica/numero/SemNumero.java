package perldoop.semantica.numero;

import perldoop.modelo.arbol.numero.Entero;
import perldoop.modelo.arbol.numero.Decimal;
import perldoop.modelo.semantica.Tipo;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de numeros
 *
 * @author César Pomar
 */
public class SemNumero {

    /*Tabla de simbolos */
    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemNumero(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(Entero s) {
        s.setTipo(new Tipo(Tipo.INTEGER));
    }

    public void visitar(Decimal s) {
        s.setTipo(new Tipo(Tipo.DOUBLE));
    }

}
