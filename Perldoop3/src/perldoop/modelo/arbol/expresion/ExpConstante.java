package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.constante.Constante;

/**
 * Clase que representa la reduccion -&gt; expresion : constante
 *
 * @author César Pomar
 */
public final class ExpConstante extends Expresion {

    private Constante constante;

    /**
     * Único contructor de la clase
     * @param constante Constante
     */
    public ExpConstante(Constante constante) {
        setConstante(constante);
    }

    /**
     * Obtiene la constante
     * @return Constante
     */
    public Constante getConstante() {
        return constante;
    }

    /**
     * Establece la constante
     * @param constante Constante
     */
    public void setConstante(Constante constante) {
        constante.setPadre(this);
        this.constante = constante;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{constante};
    }
}
