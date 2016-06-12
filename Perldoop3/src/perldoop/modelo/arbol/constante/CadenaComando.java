package perldoop.modelo.arbol.constante;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -> asignacion : CADENA_COMANDO
 *
 * @author César Pomar
 */
public final class CadenaComando extends Constante {

    private Terminal cadenaComando;

    /**
     * Único contructor de la clase
     * @param cadenaComando CadenaComando
     */
    public CadenaComando(Terminal cadenaComando) {
        setCadenaComando(cadenaComando);
    }

    /**
     * Obtiene la cadenaComando
     * @return CadenaComando
     */
    public Terminal getCadenaComando() {
        return cadenaComando;
    }

    /**
     * Establece la cadenaComando
     * @param cadenaComando CadenaComando
     */
    public void setCadenaComando(Terminal cadenaComando) {
        cadenaComando.setPadre(this);
        this.cadenaComando = cadenaComando;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{cadenaComando};
    }

}
