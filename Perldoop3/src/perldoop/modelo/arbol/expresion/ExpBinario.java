package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.binario.Binario;

/**
 * Clase que representa la reduccion -> expresion : binario
 *
 * @author César Pomar
 */
public final class ExpBinario extends Expresion {

    private Binario binario;

    public ExpBinario(Binario binario) {
        setBinario(binario);
    }
    
    /**
     * Obtiene el binario
     *
     * @return Binario
     */
    public Binario getBinario() {
        return binario;
    }

    /**
     * Establece el binario
     *
     * @param binario Binario
     */
    public void setBinario(Binario binario) {
        binario.setPadre(this);
        this.binario = binario;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{binario};
    }

}
