package perldoop.semantica.sentencia;

import perldoop.modelo.arbol.sentencia.*;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de sentencia
 *
 * @author César Pomar
 */
public class SemSentencia {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemSentencia(TablaSemantica tabla) {
        this.tabla = tabla;
    }
    
        public void visitar(StcLista s){}

    public void visitar(StcBloque s){}

    public void visitar(StcFlujo s){}

    public void visitar(StcPaquete s){}

    public void visitar(StcComentario s){}

    public void visitar(StcDeclaracion s){}

    public void visitar(StcError s){}
}
