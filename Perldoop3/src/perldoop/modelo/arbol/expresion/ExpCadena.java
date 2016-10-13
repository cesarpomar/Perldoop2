package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.cadena.Cadena;

/**
 * Clase que representa la reduccion -&gt; expresion : cadena
 *
 * @author César Pomar
 */
public final class ExpCadena extends Expresion {

    private Cadena cadena;

    /**
     * Único contructor de la clase
     * @param cadena Cadena
     */
    public ExpCadena(Cadena cadena) {
        setCadena(cadena);
    }

    /**
     * Obtiene la cadena
     * @return Cadena
     */
    public Cadena getCadena() {
        return cadena;
    }

    /**
     * Establece la cadena
     * @param cadena Cadena
     */
    public void setCadena(Cadena cadena) {
        cadena.setPadre(this);
        this.cadena = cadena;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{cadena};
    }
}
