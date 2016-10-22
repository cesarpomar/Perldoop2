package perldoop.modelo.arbol.modulos;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.paquete.Paquetes;

/**
 * Clase que representa la reduccion -&gt; modulos : USE paqueteID ID ';'
 *
 * @author César Pomar
 */
public final class ModuloUse extends Modulo {

    private Terminal idUse;
    private Paquetes paquetes;
    private Terminal puntoComa;

    /**
     * Constructor unico de la clase
     *
     * @param idUse IdUse
     * @param paquetes Paquetes
     * @param puntoComa PuntoComa
     */
    public ModuloUse(Terminal idUse, Paquetes paquetes, Terminal puntoComa) {
        setIdUse(idUse);
        setPaquetes(paquetes);
        setPuntoComa(puntoComa);
    }

    /**
     * Obtiene el idUse
     *
     * @return IdUse
     */
    public Terminal getIdUse() {
        return idUse;
    }

    /**
     * Establece el idUse
     *
     * @param idUse IdUse
     */
    public void setIdUse(Terminal idUse) {
        idUse.setPadre(this);
        this.idUse = idUse;
    }

    /**
     * Obtiene los paquetes
     *
     * @return Paquetes
     */
    public Paquetes getPaquetes() {
        return paquetes;
    }

    /**
     * Establece los paquetes
     *
     * @param paquetes Paquetes
     */
    public void setPaquetes(Paquetes paquetes) {
        paquetes.setPadre(this);
        this.paquetes = paquetes;
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
        return new Simbolo[]{idUse, paquetes, puntoComa};
    }

}
