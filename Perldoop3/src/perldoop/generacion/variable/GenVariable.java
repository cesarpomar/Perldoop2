package perldoop.generacion.variable;

import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.bloque.BloqueFor;
import perldoop.modelo.arbol.bloque.BloqueForeachVar;
import perldoop.modelo.arbol.coleccion.ColDec;
import perldoop.modelo.arbol.coleccion.ColDecOur;
import perldoop.modelo.arbol.paquete.Paquetes;
import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.arbol.variable.*;
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
     * @param publica Acceso publico
     */
    private void declararVar(Variable v, String cdec) {
        //Crear alias
        EntradaVariable e = tabla.getTablaSimbolos().buscarVariable(v.getVar().toString(), Buscar.getContexto(v));
        boolean publica = e.isPublica();
        e.setAlias(tabla.getGestorReservas().getAlias(e.getIdentificador(), e.isConflicto()));
        //Declarar
        StringBuilder declaracion = Tipos.declaracion(v.getTipo());
        declaracion.append(cdec).append(" ").append(e.getAlias()).append(v.getVar().getComentario());
        if (publica || tabla.getTablaSimbolos().getBloques() == 1) {
            if (publica) {
                declaracion.insert(0, "public static ");
            } else {
                declaracion.insert(0, "private static ");
            }
            declaracion.append(";");
            //Generar atributo
            tabla.getClase().getAtributos().add(declaracion.toString());
            if (!isAsignada(v)) {
                StringBuilder inicializacion = new StringBuilder(100);
                inicializacion.append(e.getAlias()).append('=').append(Tipos.valoreDefecto(v.getTipo()));
                v.setCodigoGenerado(inicializacion);
                Simbolo uso = Buscar.getPadre(v, 2);
                if (!(uso instanceof StcLista) && !(uso instanceof ColDec)) {
                    inicializacion.insert(0, '(').append(')');//Asegurar que permaneceran juntos
                }
            } else {
                v.setCodigoGenerado(new StringBuilder(e.getAlias()));
            }
        } else if (isAsignada(v)) {
            Igual igual = (Igual) Buscar.getPadre(v, 1);
            Simbolo uso = Buscar.getPadre(igual, 1);
            if (uso.getPadre() instanceof StcLista) {
                v.setCodigoGenerado(declaracion);
            } else if (uso instanceof BloqueFor && ((BloqueFor) uso).getLista1().getExpresiones().size() == 1
                    && ((BloqueFor) uso).getLista1().getExpresiones().get(0) == igual.getPadre()) {
                v.setCodigoGenerado(declaracion);
            } else {
                v.setCodigoGenerado(new StringBuilder(e.getAlias()));
                tabla.getDeclaraciones().add(declaracion.append(";"));
            }
        } else {
            Simbolo uso = Buscar.getPadre(v, 1);
            if (uso.getPadre() instanceof StcLista) {
                declaracion.append("=").append(Tipos.valoreDefecto(v.getTipo()));
                v.setCodigoGenerado(declaracion);
            } else if (v.getPadre() instanceof BloqueForeachVar) {
                v.setCodigoGenerado(declaracion);
            } else if (uso instanceof BloqueFor && ((BloqueFor) uso).getLista1().getExpresiones().size() == 1
                    && ((BloqueFor) uso).getLista1().getExpresiones().get(0) == v.getPadre()) {
                v.setCodigoGenerado(declaracion);
            } else {
                tabla.getDeclaraciones().add(declaracion.append(";"));
                v.getCodigoGenerado().append("=").append(Tipos.valoreDefecto(v.getTipo())).insert(0, '(').append(')');
            }
        }
    }

    /**
     * Comrpueba si Varriable sera asignado
     *
     * @param s Simbolo
     * @return Asignada
     */
    private boolean isAsignada(Variable v) {
        return Buscar.isHijo(v, Buscar.buscarPadre(v, Igual.class));
    }

}
