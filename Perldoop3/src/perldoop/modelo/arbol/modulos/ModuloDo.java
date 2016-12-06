package perldoop.modelo.arbol.modulos;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.cadena.Cadena;

/**
 * Clase que representa la reduccion -&gt; modulos : DO cadena ';'
 *
 * @author César Pomar
 */
public final class ModuloDo extends Modulo {

    private Terminal id;
    private Cadena cadena;
    private Terminal puntoComa;

    /**
     * Constructor unico de la clase
     *
     * @param id Id
     * @param cadena Cadena
     * @param puntoComa PuntoComa
     */
    public ModuloDo(Terminal id, Cadena cadena, Terminal puntoComa) {
        setId(id);
        setCadena(cadena);
        setPuntoComa(puntoComa);
    }

    /**
     * Obtiene el id
     *
     * @return Id
     */
    public Terminal getId() {
        return id;
    }

    /**
     * Establece el id
     *
     * @param id Id
     */
    public void setId(Terminal id) {
        id.setPadre(this);
        this.id = id;
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

    /**
     * Obtiene el punto y coma ';'
     *
     * @return PuntoComa
     */
    public Terminal getPuntoComa() {
        return puntoComa;
    }

    /**
     * Establece el punto y coma ';'
     *
     * @param puntoComa PuntoComa
     */
    public void setPuntoComa(Terminal puntoComa) {
        puntoComa.setPadre(this);
        this.puntoComa = puntoComa;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{id, cadena, puntoComa};
    }

}
