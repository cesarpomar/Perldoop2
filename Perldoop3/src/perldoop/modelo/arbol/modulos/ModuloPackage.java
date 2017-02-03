package perldoop.modelo.arbol.modulos;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.paquete.Paquetes;

/**
 * Clase que representa la reduccion -&gt; modulos : PACKAGE paqueteID ID
 *
 * @author César Pomar
 */
public final class ModuloPackage extends Modulo {

    private Paquetes paquetes;

    /**
     * Constructor unico de la clase
     *
     * @param id Id Package
     * @param paquetes Paquetes
     */
    public ModuloPackage(Terminal id, Paquetes paquetes) {
        super(id);
        setPaquetes(paquetes);
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

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{id, paquetes};
    }

}
