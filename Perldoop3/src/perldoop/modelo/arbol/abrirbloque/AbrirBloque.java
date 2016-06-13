package perldoop.modelo.arbol.abrirbloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -> abrirBloque :<br>
 * La función de esta clase es tener una reducción para poder abrir un nuevo 
 * contexto de variables antes de analizar el cuerpo del bloque.
 *
 * @author César Pomar
 */
public class AbrirBloque extends Simbolo{

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{};
    }
    
}
