package perldoop.generacion.funcion;

import perldoop.modelo.arbol.expresion.ExpFuncion;
import perldoop.modelo.arbol.funcion.*;
import perldoop.modelo.arbol.paquete.Paquetes;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.EntradaFuncion;
import perldoop.modelo.semantica.EntradaFuncionNoDeclarada;
import perldoop.generacion.funcion.nativa.*;
import perldoop.modelo.preprocesador.hadoop.TagsHadoopApi;
import perldoop.util.Buscar;

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
            GenFuncionNativa fn = getGenNativa(s);
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
        getGenNativa(s).visitar(s);
    }

    public void visitar(FuncionBloque s) {
        getGenNativa(s).visitar(s);
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
     * @param f Funcion
     * @return Semantica nativa
     */
    private GenFuncionNativa getGenNativa(Funcion f) {
        switch (f.getIdentificador().getValor()) {
            case "print":
                if (Buscar.getSpecial(f, TagsHadoopApi.class) != null && Buscar.getExpresiones(f.getColeccion()).size() == 4) {
                    return new GenFuncionEspPrint(tabla);
                }
                return new GenFuncionPrint(tabla);
            case "printf":
                return new GenFuncionPrintf(tabla);
            case "chop":
                return new GenFuncionChop(tabla);
            case "chomp":
                return new GenFuncionChomp(tabla);
            case "sort":
                return new GenFuncionSort(tabla);
            case "split":
                return new GenFuncionSplit(tabla);
            case "join":
                return new GenFuncionJoin(tabla);
            case "push":
                return new GenFuncionPush(tabla);
            case "pop":
                return new GenFuncionPop(tabla);
            case "shift":
                return new GenFuncionShift(tabla);
            case "unshift":
                return new GenFuncionUnshift(tabla);
            case "delete":
                return new GenFuncionDelete(tabla);
            case "keys":
                return new GenFuncionKeys(tabla);
            case "values":
                return new GenFuncionValues(tabla);
            case "die":
                return new GenFuncionDie(tabla);
            case "warn":
                return new GenFuncionWarn(tabla);
            case "exit":
                return new GenFuncionExit(tabla);
            case "open":
                return new GenFuncionOpen(tabla);
            case "close":
                return new GenFuncionClose(tabla);
            case "defined":
                return new GenFuncionDefined(tabla);
            case "undef":
                return new GenFuncionUndef(tabla);
            case "lc":
            case "lcfirst":
            case "uc":
            case "ucfirst":
                return new GenFuncionWordCase(tabla);
            default:
                return null;
        }
    }

}
