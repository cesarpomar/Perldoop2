package perldoop.semantica.funcionsub;

import perldoop.modelo.arbol.funcionsub.FuncionSub;
import perldoop.modelo.semantica.EntradaFuncion;
import perldoop.modelo.semantica.EntradaVariable;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;

/**
 * Clase para la semantica de funcionSub
 *
 * @author César Pomar
 */
public class SemFuncionSub {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemFuncionSub(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(FuncionSub s) {
        String id = s.getId().getToken().toString();
        EntradaFuncion f = new EntradaFuncion(id);
        f.setConflicto(tabla.getTablaSimbolos().buscarFuncion(id)!=null);
        tabla.getTablaSimbolos().addFuncion(f);
        tabla.getTablaSimbolos().abrirBloque();
        tabla.getTablaSimbolos().addVariable(new EntradaVariable("_", new Tipo(Tipo.ARRAY,Tipo.BOX), false), '@');
    }
}
