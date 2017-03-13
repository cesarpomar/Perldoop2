package perldoop.modelo.arbol.sentencia;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.bloque.Bloque;

/**
 * Clase que representa la reduccion -&gt; sentencia : bloque
 *
 * @author César Pomar
 */
public final class StcBloque extends Sentencia {

    private Bloque bloque;

    /**
     * Único contructor de la clase
     *
     * @param bloque Bloque
     */
    public StcBloque(Bloque bloque) {
        setBloque(bloque);
    }

    /**
     * Obtiene el bloque
     *
     * @return Bloque
     */
    public Bloque getBloque() {
        return bloque;
    }

    /**
     * Establece el bloque
     *
     * @param bloque Bloque
     */
    public void setBloque(Bloque bloque) {
        bloque.setPadre(this);
        this.bloque = bloque;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{bloque};
    }

}
