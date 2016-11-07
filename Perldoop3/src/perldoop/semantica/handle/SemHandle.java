package perldoop.semantica.handle;

import perldoop.modelo.arbol.handle.*;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.util.Tipos;

/**
 * Clase para la semantica de handle
 *
 * @author César Pomar
 */
public class SemHandle {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemHandle(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(HandleOut s) {
        s.setTipo(new Tipo(Tipo.FILE));
    }

    public void visitar(HandleErr s) {
        s.setTipo(new Tipo(Tipo.FILE));
    }

    public void visitar(HandleFile s) {
        s.setTipo(new Tipo(Tipo.FILE));
        Tipos.casting(s.getVariable(), s.getTipo(), tabla.getGestorErrores());
    }

}
