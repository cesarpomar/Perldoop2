package perldoop.semantica.funcion.nativa;

import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.arbol.funcion.FuncionBloque;
import perldoop.modelo.arbol.funcion.FuncionHandle;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Interfaz para el patron visitor de las funciones nativas
 *
 * @author César Pomar
 */
public abstract class SemFuncionNativa {

    protected TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemFuncionNativa(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    /**
     * Visita una funcion nativa
     *
     * @param f Función
     */
    public abstract void visitar(FuncionBasica f);

    /**
     * Visita una funcion nativa con Handle
     *
     * @param f Función 
     */
    public void visitar(FuncionHandle f) {
        tabla.getGestorErrores().error(Errores.FUNCION_SIN_HANDLE, f.getHandle());
        throw new ExcepcionSemantica(Errores.FUNCION_SIN_HANDLE);
    }

    /**
     * Visita una funcion nativa con un bloque
     *
     * @param f Función
     */
    public void visitar(FuncionBloque f) {
        tabla.getGestorErrores().error(Errores.FUNCION_SIN_HANDLE, f.getLlaveD());
        throw new ExcepcionSemantica(Errores.FUNCION_SIN_HANDLE);
    }

}
