package perldoop.generacion.funcionsub;

import perldoop.modelo.arbol.funcionsub.FuncionSub;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.EntradaFuncion;

/**
 * Clase generadora de funcionSub
 *
 * @author César Pomar
 */
public class GenFuncionSub {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenFuncionSub(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(FuncionSub s) {
        StringBuilder codigo = new StringBuilder(50);
        EntradaFuncion fun = tabla.getTablaSimbolos().buscarFuncion(s.getId().getToken().toString());
        if (fun.getAlias() == null) {
            fun.setAlias(tabla.getGestorReservas().getAlias(fun.getIdentificador(), fun.isConflicto()));
        }
        codigo.append("public static Box[] ").append(fun.getAlias()).append(" (Box[] __)");
        s.setCodigoGenerado(codigo);
    }

}
