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
public final class FuncionArgs extends Funcion {

    private Argumentos argumentos;

    /**
     * Único contructor de la clase
     *
     * @param identificador Identificador
     * @param argumentos Argumentos
     */
    public FuncionArgs(Terminal identificador, Argumentos argumentos) {
        super(identificador);
        setArgumentos(argumentos);
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
        return new Simbolo[]{identificador, argumentos};
    }

}
