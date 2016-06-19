package perldoop.modelo.arbol.funcion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -> <br>
 * variable : paquete ID expresion<br>
 * | ID expresion<br>
 *
 * @author César Pomar
 */
public final class FuncionPaqueteArgs extends Funcion {

    private Terminal paquete;
    private Terminal ambito;
    private Argumentos argumentos;

    /**
     * Único contructor de la clase
     *
     * @param paquete Paquete
     * @param ambito Ambito
     * @param identificador Identificador
     * @param argumentos Argumentos
     */
    public FuncionPaqueteArgs(Terminal paquete, Terminal ambito, Terminal identificador, Argumentos argumentos) {
        super(identificador);
        setPaquete(paquete);
        setAmbito(ambito);
        setArgumentos(argumentos);
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

    /**
     * Obtiene los argumentos
     *
     * @return Argumentos
     */
    public Argumentos getArgumentos() {
        return argumentos;
    }

    /**
     * Establece los argumentos
     *
     * @param argumentos Argumentos
     */
    public void setArgumentos(Argumentos argumentos) {
        argumentos.setPadre(this);
        this.argumentos = argumentos;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{paquete, ambito, identificador, argumentos};
    }

}
