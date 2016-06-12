package perldoop.modelo.arbol.constante;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -> asignacion : CADENA_DOBLE
 *
 * @author César Pomar
 */
public final class CadenaDoble extends Constante {

    private Terminal cadenaDoble;

    /**
     * Único contructor de la clase
     *
     * @param cadenaDoble CadenaDoble
     */
    public CadenaDoble(Terminal cadenaDoble) {
        setCadenaDoble(cadenaDoble);
    }

    /**
     * Obtiene la cadenaDoble
     *
     * @return CadenaDoble
     */
    public Terminal getCadenaDoble() {
        return cadenaDoble;
    }

    /**
     * Establece la cadenaDoble
     *
     * @param cadenaDoble CadenaDoble
     */
    public void setCadenaDoble(Terminal cadenaDoble) {
        cadenaDoble.setPadre(this);
        this.cadenaDoble = cadenaDoble;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{cadenaDoble};
    }

}
