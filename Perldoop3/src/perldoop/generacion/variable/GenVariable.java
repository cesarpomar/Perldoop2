package perldoop.generacion.variable;

import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.bloque.BloqueFor;
import perldoop.modelo.arbol.bloque.BloqueForeachVar;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.paquete.Paquetes;
import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.arbol.variable.*;
import perldoop.modelo.generacion.Declaracion;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.EntradaVariable;
import perldoop.util.Buscar;

/**
 * Clase generadora de variable
 *
 * @author César Pomar
 */
public final class GenVariable {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenVariable(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(VarExistente s) {
        if (Buscar.isVariableSort(s)) {
            s.setCodigoGenerado(new StringBuilder(s.getVar().getValor()));
        } else if (Buscar.isDeclaracion(s)) {
            declararVar(s, "");
        } else {
            EntradaVariable e = tabla.getTablaSimbolos().buscarVariable(s.getVar().getValor(), Buscar.getContexto(s));
            s.setCodigoGenerado(new StringBuilder(e.getAlias()).append(s.getVar().getComentario()));
        }
    }

    public void visitar(VarPaquete s) {
        EntradaVariable e = tabla.getTablaSimbolos().buscarVariable(s.getVar().getValor(), Buscar.getContexto(s));
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(getPaquete(s.getPaquetes()));
        codigo.append(".").append(e.getAlias()).append(s.getVar().getComentario());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(VarSigil s) {
        EntradaVariable e = tabla.getTablaSimbolos().buscarVariable(s.getVar().getValor(), Buscar.getContexto(s));
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getContexto().getComentario());
        codigo.append(s.getSigil().getComentario());
        codigo.append("(").append(e.getAlias()).append(s.getVar().getComentario());
        if (e.getTipo().isArray()) {
            codigo.append(".length - 1)");
        } else {
            codigo.append(".size() - 1)");
        }
        s.setCodigoGenerado(codigo);
    }

    public void visitar(VarPaqueteSigil s) {
        EntradaVariable e = tabla.getTablaSimbolos().buscarVariable(s.getVar().getValor(), Buscar.getContexto(s));
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(getPaquete(s.getPaquetes()));
        codigo.append(s.getSigil().getComentario());
        codigo.append("(").append(e.getAlias()).append(s.getVar().getComentario());
        if (e.getTipo().isArray()) {
            codigo.append(".length - 1)");
        } else {
            codigo.append(".size() - 1)");
        }
        s.setCodigoGenerado(codigo);
    }

    public void visitar(VarMy s) {
        declararVar(s, s.getMy().getComentario());
    }

    public void visitar(VarOur s) {
        declararVar(s, s.getOur().getComentario());
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
     * Declara una variable
     *
     * @param v Variable
     * @param cdec Comentario terminal de declaracion
     */
    private void declararVar(Variable v, String cdec) {
        //Crear alias
        EntradaVariable e = tabla.getTablaSimbolos().buscarVariable(v.getVar().toString(), Buscar.getContexto(v));
        boolean publica = e.isPublica();
        e.setAlias(tabla.getGestorReservas().getAlias(e.getIdentificador(), e.isConflicto()));
        //Declarar
        if (publica || tabla.getTablaSimbolos().getBloques() == 1) {
            StringBuilder atributo = new StringBuilder(100);
            atributo.append(publica ? "public static " : "private static ").append(Tipos.declaracion(v.getTipo()));
            atributo.append(cdec).append(" ").append(e.getAlias()).append(v.getVar().getComentario());
            atributo.append(";");
            tabla.getClase().getAtributos().add(atributo.toString());
            if (!isAsignada(v)) {
                if (isSentencia(v)) {
                    v.setCodigoGenerado(new StringBuilder(100).append(e.getAlias()).append("=").append("null"));
                } else {
                    tabla.getDeclaraciones().add(new Declaracion(v, e.getAlias(), "null"));
                    v.setCodigoGenerado(new StringBuilder(e.getAlias()));
                }
            } else {
                v.setCodigoGenerado(new StringBuilder(e.getAlias()));
            }
        } else if (isSentencia(v) || isFor(v) || isForEach(v)) {
            StringBuilder codigo = Tipos.declaracion(v.getTipo());
            codigo.append(cdec).append(" ").append(e.getAlias()).append(v.getVar().getComentario());
            if (!isAsignada(v)) {
                if (!isForEach(v)) {
                    codigo.append("=").append("null");
                }
            }
            v.setCodigoGenerado(codigo);
        } else {
            String def = null;
            if (!isAsignada(v)) {
                def = "null";
            }
            tabla.getDeclaraciones().add(new Declaracion(v, v.getTipo(), e.getAlias(), def));
            v.setCodigoGenerado(new StringBuilder(100).append(cdec).append(e.getAlias()).append(v.getVar().getComentario()));
        }
    }

    /**
     * Comprueba si Variable sera asignada
     *
     * @param v Simbolo variable
     * @return Asignada
     */
    private boolean isAsignada(Variable v) {
        Igual igual = Buscar.buscarPadre(v, Igual.class);
        return igual != null && Buscar.isHijo(v, igual.getIzquierda());
    }

    /**
     * Comprueba si Variable esta contenida en el bloque de inicializacion for
     *
     * @param v Simbolo variable
     * @return Sentencia
     */
    private boolean isFor(Variable v) {
        BloqueFor bloque = Buscar.buscarPadre(v, BloqueFor.class);
        return bloque != null && bloque.getLista1().getExpresiones().size() == 1 && Buscar.isHijo(v, bloque.getLista1());
    }

    /**
     * Comprueba si Varriable esta contenida en el bloque de inicializacion for
     *
     * @param v Simbolo variable
     * @return Sentencia
     */
    private boolean isForEach(Variable v) {
        return v.getPadre() instanceof BloqueForeachVar;
    }

    /**
     * Comprueba si Variable sera una sentencia
     *
     * @param v Simbolo variable
     * @return Sentencia
     */
    private boolean isSentencia(Variable v) {
        if (v.getPadre() instanceof Expresion) {
            Simbolo uso = Buscar.getUso((Expresion) v.getPadre());
            if (uso instanceof Igual) {
                return Buscar.getUso((Expresion) uso.getPadre()).getPadre() instanceof StcLista;//Inicializaciones
            }
            if (v instanceof VarExistente && Buscar.isDeclaracion(v)) {
                return true;
            }
            return uso.getPadre() instanceof StcLista;
        }
        return false;
    }

}
