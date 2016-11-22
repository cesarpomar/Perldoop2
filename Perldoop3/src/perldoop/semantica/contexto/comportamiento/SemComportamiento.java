package perldoop.semantica.contexto.comportamiento;


import perldoop.modelo.arbol.contexto.Contexto;
import perldoop.modelo.semantica.TablaSemantica;


/**
 * Clase para la semantica de comportamientos de bloques
 *
 * @author César Pomar
 */
public abstract class SemComportamiento {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemComportamiento(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    /**
     * Visita un contexto
     * @param s Contexto
     */
    public abstract void visitar(Contexto s);

}
