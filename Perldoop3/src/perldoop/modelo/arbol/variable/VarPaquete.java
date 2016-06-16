package perldoop.modelo.arbol.variable;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -> <br>variable : '$' paquete ID<br>
 * '@' paquete ID<br>
 * '%' paquete ID
 *
 * @author César Pomar
 */
public final class VarPaquete extends Variable {

    private Terminal paquete;
    private Terminal ambito;

    /**
     * Único contructor de la clase
     *
     * @param contexto Contexto
     * @param paquete Paquete
     * @param ambito Ambito
     * @param var Variable
     */
    public VarPaquete(Terminal contexto, Terminal paquete, Terminal ambito, Terminal var) {
        super(contexto, var);
        setPaquete(paquete);
        setAmbito(ambito);
    }

    /**
     * Obtiene el paquete
     *
     * @return Paquete
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
        paquete.setPadre(this);
        this.paquete = paquete;
    }

    /**
     * Obtiene el ambito
     *
     * @return Ambito
     */
    public Terminal getAmbito() {
        return ambito;
    }

    /**
     * Establece el ambito
     *
     * @param ambito Ambito
     */
    public void setAmbito(Terminal ambito) {
        ambito.setPadre(this);
        this.ambito = ambito;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{paquete, var};
    }
}
