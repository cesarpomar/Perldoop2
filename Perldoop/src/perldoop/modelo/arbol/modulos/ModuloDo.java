package perldoop.modelo.arbol.modulos;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.cadena.Cadena;

/**
 * Clase que representa la reduccion -&gt; modulos : DO cadena
 *
 * @author César Pomar
 */
public final class ModuloDo extends Modulo {

    private Cadena cadena;

    /**
     * Constructor unico de la clase
     *
     * @param id Id
     * @param cadena Cadena
     */
    public ModuloDo(Terminal id, Cadena cadena) {
        super(id);
        setCadena(cadena);
    }

    /**
     * Obtiene la cadena
     *
     * @return Cadena
     */
    public Cadena getCadena() {
        return cadena;
    }

    /**
     * Establece la cadena
     *
     * @param cadena Cadena
     */
    public void setCadena(Cadena cadena) {
        this.cadena = cadena;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{id, cadena};
    }

}
