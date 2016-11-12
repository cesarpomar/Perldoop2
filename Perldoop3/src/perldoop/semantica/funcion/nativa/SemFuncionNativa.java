package perldoop.semantica.funcion.nativa;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.Funcion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.arbol.funcion.FuncionBloque;
import perldoop.modelo.arbol.funcion.FuncionHandle;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.coleccion.SemColeccion;
import perldoop.semantica.util.Tipos;
import perldoop.util.Buscar;
import perldoop.util.ParserEtiquetas;

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

    /**
     * Comprueba que la expresion es una variable y que cumple con el tipo especificado
     *
     * @param exp Expresion
     * @param t Tipo, si es nulo se obvia esta comprobacion
     */
    protected void checkVariable(Expresion exp, Tipo t) {
        if (!Buscar.isVariable(Buscar.getExpresion(exp))) {
            tabla.getGestorErrores().error(Errores.FUNCION_VARIABLE, Buscar.tokenInicio(exp));
            throw new ExcepcionSemantica(Errores.FUNCION_VARIABLE);
        }
        if (t != null) {
            //Evitar conversion col to escalar
            if (exp.getTipo().isColeccion() != t.isColeccion()) {
                errorVariableTipo(exp, t);
            }
            //Comprobar resto de lo casting
            try {
                Tipos.casting(exp, t, tabla.getGestorErrores());
            } catch (ExcepcionSemantica es) {
                errorVariableTipo(exp, t);
            }
        }
    }

    /**
     * Lanza un error cuando la variable no es compatible con el tipo
     *
     * @param exp Expresion de la variable
     * @param t Tipo esperador
     */
    protected void errorVariableTipo(Expresion exp, Tipo... t) {
        tabla.getGestorErrores().error(Errores.FUNCION_VARIABLE_TIPO, Buscar.tokenInicio(exp),
                Arrays.stream(t).map(ti -> String.join("", ParserEtiquetas.parseTipo(ti))).collect(Collectors.joining("|"))
        );
        throw new ExcepcionSemantica(Errores.FUNCION_VARIABLE_TIPO);
    }

    /**
     * Comprueba que la funcion solo tenga el numero de argumentos en un rango acotado
     *
     * @param f Funcion
     * @param min Argumentos minimos
     * @param max Argumentos maximos
     */
    protected void checkArgumentos(Funcion f, Integer min, Integer max) {
        int cn = Buscar.getExpresiones(f.getColeccion()).size();
        if (max != null && cn > max) {
            tabla.getGestorErrores().error(Errores.FUNCION_NUM_ARGS, f.getColeccion().getLista().getSeparadores().get(cn).getToken(),
                    f.getIdentificador().getValor(), max, cn);
            throw new ExcepcionSemantica(Errores.FUNCION_NUM_ARGS);
        }
        if (min != null && cn < min) {
            tabla.getGestorErrores().error(Errores.FUNCION_NUM_ARGS, f.getIdentificador().getToken(),
                    f.getIdentificador().getValor(), min, cn);
            throw new ExcepcionSemantica(Errores.FUNCION_NUM_ARGS);
        }
    }

    /**
     * Comprueba si una lista de argumentos pueden convertirse a la coleccion deseada
     *
     * @param tc Tipo de la coleccion
     * @param lista Lista de expresiones
     */
    protected void checkTipoArgs(Tipo tc, List<Expresion> lista) {
        SemColeccion.comprobarElems(tc, lista, tabla);
    }

}
