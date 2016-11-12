package perldoop.modelo.arbol.lectura;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; lectura : '-&lt;' expresion '-&gt;'
 *
 * @author César Pomar
 */
public final class LecturaFile extends Lectura {

    private Expresion expresion;

    /**
     * Constructor unico de la clase
     *
     * @param menor Menor
     * @param expresion Expresion
     * @param mayor Mayor
     */
    public LecturaFile(Terminal menor, Expresion expresion, Terminal mayor) {
        super(menor, mayor);
        setExpresion(expresion);
    }

    /**
     * Obtiene la expresion
     *
     * @return Expresion
     */
    public Expresion getExpresion() {
        return expresion;
    }

    /**
     * Establece la expresion
     *
     * @param expresion Expresion
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
        return new Simbolo[]{menor, expresion, mayor};
    }

}
