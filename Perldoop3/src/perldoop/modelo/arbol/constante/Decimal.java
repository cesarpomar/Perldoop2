package perldoop.modelo.arbol.constante;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; asignacion : DECIMAL
 *
 * @author César Pomar
 */
public final class Decimal extends Constante {

      private Terminal decimal;

    /**
     * Único contructor de la clase
     * @param decimal Decimal
     */
    public Decimal(Terminal decimal) {
        setDecimal(decimal);
    }

    /**
     * Obtiene la decimal
     * @return Decimal
     */
    public Terminal getDecimal() {
        return decimal;
    }

    /**
     * Establece la decimal
     * @param decimal Decimal
     */
    public void setDecimal(Terminal decimal) {
        decimal.setPadre(this);
        this.decimal = decimal;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{decimal};
    }

}
