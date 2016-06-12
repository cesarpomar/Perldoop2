package perldoop.modelo.arbol.sentencia;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -> sentencia : PACKAGE ID ';'
 *
 * @author César Pomar
 */
public final class StcPaquete extends Sentencia {

    private Terminal paquete;
    private Terminal id;
    private Terminal puntoComa;

    /**
     * nico contructor de la clase
     *
     * @param paquete Paquete
     * @param id Id
     * @param puntoComa PuntoComa
     */
    public StcPaquete(Terminal paquete, Terminal id, Terminal puntoComa) {
        setPaquete(paquete);
        setId(id);
        setPuntoComa(puntoComa);
    }

    /**
     * Obtiene el paquete
     *
     * @return paquete
     */
    public Terminal getPaquete() {
        return paquete;
    }

    /**
     * Establece el paquete
     *
     * @param paquete Paquete
     */
    public void setPaquete(Terminal paquete) {
        this.paquete = paquete;
    }

    /**
     * obtiene el id
     *
     * @return Id
     */
    public Terminal getId() {
        return id;
    }

    /**
     * Establece elid
     *
     * @param id Id
     */
    public void setId(Terminal id) {
        this.id = id;
    }

    /**
     * Obtiene el punto y coma ';'
     *
     * @return PuntoComa
     */
    public final Terminal getPuntoComa() {
        return puntoComa;
    }

    /**
     * Establece el punto y coma ';'
     *
     * @param puntoComa PuntoComa
     */
    public final void setPuntoComa(Terminal puntoComa) {
        puntoComa.setPadre(this);
        this.puntoComa = puntoComa;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{paquete, id, puntoComa};
    }
}
