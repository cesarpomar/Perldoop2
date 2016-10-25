package perldoop.generacion.funcion.nativa;

import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.arbol.funcion.FuncionBloque;
import perldoop.modelo.arbol.funcion.FuncionHandle;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Interfaz para el patron visitor de las funciones nativas
 *
 * @author César Pomar
 */
public abstract class GenFuncionNativa {

    protected TablaGenerador tabla;

    /**
     * Contruye el generador
     *
     * @param tabla Tabla de simbolos
     */
    public GenFuncionNativa(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    /**
     * Visita una funcion nativa
     *
     * @param f Función
     */
    public abstract void visitar(FuncionBasica f);

    /**
     * Visita una funcion nativa
     *
     * @param f Función
     */
    public void visitar(FuncionHandle f) {
    }

    /**
     * Visita una funcion nativa
     *
     * @param f Función
     */
    public void visitar(FuncionBloque f) {
    }

}
