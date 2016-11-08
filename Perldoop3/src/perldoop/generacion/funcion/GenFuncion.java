package perldoop.generacion.funcion;

import perldoop.modelo.arbol.expresion.ExpFuncion;
import perldoop.modelo.arbol.funcion.*;
import perldoop.modelo.arbol.paquete.Paquetes;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.EntradaFuncion;
import perldoop.modelo.semantica.EntradaFuncionNoDeclarada;
import perldoop.generacion.funcion.nativa.*;

/**
 * Clase generadora de funcion
 *
 * @author César Pomar
 */
public final class GenFuncion {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenFuncion(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(FuncionBasica s) {
        if (s.getPadre() instanceof ExpFuncion) {
            GenFuncionNativa fn = getGenNativa(s.getIdentificador().getValor());
            if (fn != null) {
                fn.visitar(s);
                return;
            }
        }
        StringBuilder codigo = new StringBuilder(100);
        if (!s.getPaquetes().isVacio()) {
            codigo.append(getPaquete(s.getPaquetes()));
            codigo.append(".");
        }
        codigo.append(getFuncion(s.getIdentificador().getValor(), s.getPaquetes()));
        codigo.append(s.getIdentificador().getComentario());
        codigo.append("(").append(s.getColeccion()).append(")");
        s.setCodigoGenerado(codigo);
    }

    public void visitar(FuncionHandle s) {
        getGenNativa(s.getIdentificador().getValor()).visitar(s);
    }

    public void visitar(FuncionBloque s) {
        getGenNativa(s.getIdentificador().getValor()).visitar(s);
    }

    /**
     * Obtiene el paquete
     *
     * @param id Identificador
     * @param p Paquetes
     * @return Nombre paquete
     */
    private String getFuncion(String id, Paquetes p) {
        if (!p.isVacio()) {
            return tabla.getTablaSimbolos().getImports().get(p.getClaseJava()).getFunciones().get(id).getAlias();
        }
        EntradaFuncion funcion = tabla.getTablaSimbolos().buscarFuncion(id);
        if (funcion == null) {
            EntradaFuncionNoDeclarada entrada = tabla.getTablaSimbolos().buscarFuncionNoDeclarada(id);
            if (entrada.getAlias() == null) {
                entrada.setAlias(tabla.getGestorReservas().getAlias(id, false));
            }
            return entrada.getAlias();
        } else {
            return funcion.getAlias();
        }
    }

    /**
     * Obtiene el paquete
     *
     * @param p Paquetes
     * @return Nombre paquete
     */
    private String getPaquete(Paquetes p) {
        return tabla.getTablaSimbolos().getImports().get(p.getClaseJava()).getAlias();
    }

    /**
     * Obtiene la semantica nativa de una funcion
     *
     * @param id Nombre de la funcion
     * @return Semantica nativa
     */
    private GenFuncionNativa getGenNativa(String id) {
        switch (id) {
            case "print":
                return new GenFuncionPrint(tabla);
            case "chop":
                return new GenFuncionChop(tabla);
            case "chomp":
                return new GenFuncionChomp(tabla);
            case "sort":
                return new GenFuncionSort(tabla);
            default:
                return null;
        }
    }

}
