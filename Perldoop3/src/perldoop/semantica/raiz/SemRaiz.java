package perldoop.semantica.raiz;

import java.util.Collection;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Raiz;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.semantica.EntradaFuncionNoDeclarada;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de raiz
 *
 * @author César Pomar
 */
public class SemRaiz {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemRaiz(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(Raiz s) {
        //Imprimimos como error todas las funciones usadas pero no declaradas
        Collection<EntradaFuncionNoDeclarada> funciones = tabla.getTablaSimbolos().getFuncionesNoDeclaradas().values();
        for (EntradaFuncionNoDeclarada funcion : funciones) {
            for (Token token : funcion.getLlamadas()) {
                tabla.getGestorErrores().error(Errores.FUNCION_NO_EXISTE, token, token.getValor());
            }
        }
        if (!funciones.isEmpty()) {
            throw new ExcepcionSemantica(Errores.FUNCION_NO_EXISTE);
        }
    }

}
