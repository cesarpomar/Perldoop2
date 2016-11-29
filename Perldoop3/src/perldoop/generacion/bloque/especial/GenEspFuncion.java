package perldoop.generacion.bloque.especial;

import java.util.ArrayList;
import java.util.List;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.arbol.bloque.BloqueVacio;
import perldoop.modelo.arbol.bloque.SubBloque;
import perldoop.modelo.arbol.bloque.SubBloqueVacio;
import perldoop.modelo.arbol.variable.Variable;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.EntradaVariable;
import perldoop.util.Buscar;

/**
 * Clase generadora de bloque funcion
 *
 * @author César Pomar
 */
public final class GenEspFuncion extends GenEspecial {

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenEspFuncion(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(Bloque s) {
        StringBuilder cuerpo = s.getCodigoGenerado();
        StringBuilder codigo = new StringBuilder();
        StringBuilder funcion = new StringBuilder();
        String nombre = tabla.getGestorReservas().getAux("function");
        //Buscamos y generamos las variables externas
        List<StringBuilder> args = new ArrayList<>();
        List<String> vars = new ArrayList<>();
        List<StringBuilder> returns = new ArrayList<>();
        for (Variable var : Buscar.buscarDependencias(s)) {
            EntradaVariable entrada = tabla.getTablaSimbolos().buscarVariable(var.getVar().getValor(), Buscar.getContexto(var));
            //Las globales son accesibles igualmente
            if (entrada.getNivel() == 0) {
                continue;
            }
            StringBuilder dec = Tipos.declaracion(entrada.getTipo());
            vars.add(entrada.getAlias());
            returns.add(new StringBuilder(100).append(entrada.getAlias()).append("=(").append(dec).append(")"));
            args.add(new StringBuilder(100).append(dec).append(" ").append(entrada.getAlias()));
        }
        //Actualizacion de variables por retorno
        if (!returns.isEmpty()) {
            String aux = tabla.getGestorReservas().getAux();
            codigo.append("Object[]").append(aux).append("=").append(nombre).append('(').append(String.join(",", vars)).append(");");
            int i = 0;
            for (StringBuilder var : returns) {
                codigo.append(var).append(aux).append('[').append(i++).append(']').append(";");
            }
        } else {
            codigo.append(nombre).append('(').append(String.join(",", vars)).append(");");
        }
        s.setCodigoGenerado(codigo);
        //Generamos la nueva funcion
        funcion.append("private static Object[] ").append(nombre).append("(").append(String.join(",", args)).append(")");
        if (s instanceof BloqueVacio || (s instanceof SubBloque && !(s instanceof SubBloqueVacio))) {
            funcion.append(s.getLlaveI());
            funcion.append(s.getCuerpo());
            funcion.append("return new Object[]{").append(String.join(",", vars)).append("};");
            funcion.append(s.getLlaveD());

        } else {
            funcion.append('{').append(cuerpo);
            funcion.append("return new Object[]{").append(String.join(",", vars)).append("};");
            funcion.append('}');
        }
        tabla.getClase().getFunciones().add(funcion);
    }

}
