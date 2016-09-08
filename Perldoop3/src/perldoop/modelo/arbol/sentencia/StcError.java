package perldoop.modelo.arbol.sentencia;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; sentencia : error ';'<br>
 * Esta es la recuperación de un error en el analisis sintactico, la unica
 * finalidad es rellenar el arbol sintactico para poder continuar con el
 * analisis en busca de otros errores.
 *
 * @author César Pomar
 */
public final class StcError extends Sentencia {
    
    @Override
    public void aceptar(Visitante v) {
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{};
    }

}
