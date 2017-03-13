package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.lectura.Lectura;

/**
 * Clase que representa la reduccion -&gt; expresion : lectura
 *
 * @author César Pomar
 */
public class ExpLectura extends Expresion {

    private Lectura lectura;

    /**
     * Único contructor de la clase
     *
     * @param lectura Lectura
     */
    public ExpLectura(Lectura lectura) {
        setLectura(lectura);
    }

    /**
     * Obtiene la lectura
     *
     * @return Lectura
     */
    public final Lectura getLectura() {
        return lectura;
    }

    /**
     * Establece la lectura
     *
     * @param lectura Lectura
     */
    public final void setLectura(Lectura lectura) {
        lectura.setPadre(this);
        this.lectura = lectura;
    }

    @Override
    public Simbolo getValor() {
        return lectura;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{lectura};
    }
}
