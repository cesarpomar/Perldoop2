package perldoop.modelo.arbol.modulos;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.paquete.Paquetes;

/**
 * Clase que representa la reduccion -&gt; modulos : PACKAGE paqueteID ID ';'
 *
 * @author César Pomar
 */
public final class ModuloPackage extends Modulo {

    private Terminal idPackage;
    private Paquetes paquetes;
    private Terminal id;
    private Terminal puntoComa;

    /**
     * Constructor unico de la clase
     *
     * @param idPackage idPackage
     * @param paquetes Paquetes
     * @param id Id
     * @param puntoComa PuntoComa
     */
    public ModuloPackage(Terminal idPackage, Paquetes paquetes, Terminal id, Terminal puntoComa) {
        setIdPackage(idPackage);
        setPaquetes(paquetes);
        setId(id);
        setPuntoComa(puntoComa);
    }

    /**
     * Obtiene el idPackage
     *
     * @return IdPackage
     */
    public Terminal getIdPackage() {
        return idPackage;
    }

    /**
     * Establece el idPackage
     *
     * @param idPackage IdPackage
     */
    public void setIdPackage(Terminal idPackage) {
        idPackage.setPadre(this);
        this.idPackage = idPackage;
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
        return new Simbolo[]{idPackage, paquetes, id, puntoComa};
    }

}
