package perldoop.generacion.funcion.nativa;

import perldoop.generacion.acceso.GenAcceso;
import perldoop.generacion.util.Casting;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.expresion.ExpAcceso;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.Funcion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.arbol.funcion.FuncionBloque;
import perldoop.modelo.arbol.funcion.FuncionHandle;
import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

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

    /**
     * Genera una variable auxiliar cuando se necesita usar el retorno para actualizar variables
     *
     * @param f Funcion
     * @param codigo Codigo
     * @return Variable auxiliar
     */
    protected String genReturnVar(Funcion f, StringBuilder codigo) {
        if (Buscar.getUso((Expresion) f.getPadre()).getPadre() instanceof StcLista) {
            codigo.append(")");
            return null;
        }
        String aux = tabla.getGestorReservas().getAux();
        Tipo t = f.getTipo().getSubtipo(0).add(0, Tipo.REF);
        tabla.getDeclaraciones().add(Tipos.declaracion(t).append(" ").append(aux).append(";"));
        codigo.append(",").append(aux).append("=new Ref<>())");
        return aux;
    }

    /**
     * Genera la expresion retorno de la funcion usando la variable auxiliar
     *
     * @param aux Variable auxiliar retornada en la funcion genReturnVar
     * @param codigo Codigo
     */
    protected void genReturn(String aux, StringBuilder codigo) {
        if (aux != null) {
            codigo.insert(0, "Pd.rn(").append(",").append(aux).append(")");
        }
    }

    /**
     * Genera la lectura y la escritura para una variable que necesita ser actualizada
     *
     * @param exp
     * @param lectura
     * @param escritura
     */
    protected void genVariable(Expresion exp, Simbolo lectura, Simbolo escritura) {
        Expresion aux = Buscar.getExpresion(exp);
        if (aux instanceof ExpAcceso) {
            GenAcceso.getReplica((ExpAcceso) exp, lectura, escritura, tabla);
        }
    }

    protected StringBuilder updateVariable(Expresion exp, Simbolo escritura, Simbolo valor) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(escritura);
        if (Buscar.isArrayOrVar(exp)) {
            codigo.append("=").append(Casting.casting(valor, escritura.getTipo()));
        } else {
            codigo.append(Casting.casting(valor, escritura.getTipo())).append(")");
        }
        return codigo;
    }

}
