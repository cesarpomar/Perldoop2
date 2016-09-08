package perldoop.modelo.arbol.constante;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; asignacion : CADENA_SIMPLE
 *
 * @author César Pomar
 */
public final class CadenaSimple extends Constante {

    private Terminal cadenaSimple;

    /**
     * Único contructor de la clase
     *
     * @param cadenaSimple CadenaComando
     */
    public CadenaSimple(Terminal cadenaSimple) {
        setCadenaSimple(cadenaSimple);
    }

    /**
     * Obtiene la cadenaSimple
     *
     * @return CadenaSimple
     */
    public Terminal getCadenaSimple() {
        return cadenaSimple;
    }

    /**
     * Establece la cadenaSimple
     *
     * @param cadenaSimple CadenaSimple
     */
    public void setCadenaSimple(Terminal cadenaSimple) {
        cadenaSimple.setPadre(this);
        this.cadenaSimple = cadenaSimple;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{cadenaSimple};
    }

}
