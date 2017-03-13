package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.numero.Numero;

/**
 * Clase que representa la reduccion -&gt; expresion : numero
 *
 * @author César Pomar
 */
public final class ExpNumero extends Expresion {

    private Numero numero;

    /**
     * Único contructor de la clase
     *
     * @param numero Numero
     */
    public ExpNumero(Numero numero) {
        setNumero(numero);
    }

    /**
     * Obtiene el numero
     *
     * @return Numero
     */
    public Numero getNumero() {
        return numero;
    }

    /**
     * Establece el numero
     *
     * @param numero Numero
     */
    public void setNumero(Numero numero) {
        numero.setPadre(this);
        this.numero = numero;
    }

    @Override
    public Simbolo getValor() {
        return numero;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{numero};
    }
}
