package perldoop.modelo.arbol.funcion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.paquete.Paquetes;

/**
 * Clase que representa la reduccion -> <br>
 * variable : paquete ID expresion<br>
 * | ID expresion<br>
 *
 * @author César Pomar
 */
public final class FuncionPaqueteArgs extends Funcion {

    private Paquetes paquetes;
    private Argumentos argumentos;

    /**
     * Único contructor de la clase
     *
     * @param paquetes paquetes
     * @param identificador Identificador
     * @param argumentos Argumentos
     */
    public FuncionPaqueteArgs(Paquetes paquetes, Terminal identificador, Argumentos argumentos) {
        super(identificador);
        setPaquetes(paquetes);
        setArgumentos(argumentos);
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
        return new Simbolo[]{paquetes, identificador, argumentos};
    }

}
