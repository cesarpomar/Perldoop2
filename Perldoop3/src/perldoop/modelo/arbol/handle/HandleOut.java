package perldoop.modelo.arbol.handle;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; handle : STDOUT
 *
 * @author César Pomar
 */
public final class HandleOut extends Handle {

    private Terminal salida;

    /**
     * Constructor unico de la clase
     *
     * @param salida Salida
     */
    public HandleOut(Terminal salida) {
        setSalida(salida);
    }

    /**
     * Obtiene la salida
     *
     * @return salida
     */
    public Terminal getSalida() {
        return salida;
    }

    /**
     * Establece la salida
     *
     * @param salida Salida
     */
    public void setSalida(Terminal salida) {
        salida.setPadre(this);
        this.salida = salida;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{salida};
    }

}
