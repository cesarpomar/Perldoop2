package perldoop.generacion.variable;

import java.util.Arrays;
import java.util.List;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.variable.*;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.expresion.ExpAsignacion;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.sentencia.StcLista;
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
        EntradaVariable e = tabla.getTablaSimbolos().buscarVariable(s.getVar().getValor(), Buscar.getContexto(s));
        s.setCodigoGenerado(new StringBuilder(e.getAlias()).append(s.getVar().getComentario()));
    }

    public void visitar(VarPaquete s) {
        EntradaVariable e = tabla.getTablaSimbolos().buscarVariable(s.getVar().getValor(), Buscar.getContexto(s));
        StringBuilder codigo = new StringBuilder(s.getPaquetes().getCodigoGenerado());
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
        StringBuilder codigo = new StringBuilder(s.getPaquetes().getCodigoGenerado());
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

    public void visitar(VarMy s) {
        declararVar(s, s.getMy(), false);
    }

    public void visitar(VarOur s) {
        declararVar(s, s.getOur(), true);
    }

    /**
     * Declara una variable
     *
     * @param v Variable
     * @param dec Terminal de declaracion
     * @param publica Acceso publico
     */
    public void declararVar(Variable v, Terminal dec, boolean publica) {
        //Crear alias
        EntradaVariable e = tabla.getTablaSimbolos().buscarVariable(v.getVar().toString(), Buscar.getContexto(v));
        e.setAlias(tabla.getGestorReservas().getAlias(e.getIdentificador(), e.isConflicto()));
        //Declarar
        StringBuilder declaracion = Tipos.declaracion(v.getTipo());
        declaracion.append(dec.getComentario()).append(" ").append(e.getAlias()).append(v.getVar().getComentario());
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
                if (!(Buscar.getPadre(v, 2) instanceof StcLista)) {
                    inicializacion.insert(0, '(').append(')');//Asegurar que permaneceran juntos
                }
            } else {
                v.setCodigoGenerado(new StringBuilder(e.getAlias()));
            }
        } else {
            if (Buscar.getPadre(v, 2) instanceof StcLista) {
                declaracion.append("=").append(Tipos.valoreDefecto(v.getTipo()));
                v.setCodigoGenerado(declaracion);
            } else {               
                if(isAsignada(v)){
                    if(Buscar.isCamino(v, Expresion.class,Igual.class,ExpAsignacion.class,Lista.class,StcLista.class)){
                        v.setCodigoGenerado(declaracion);
                    }else{
                        v.setCodigoGenerado(new StringBuilder(e.getAlias()));
                        tabla.getDeclaraciones().add(declaracion.append(";"));
                    }           
                }else{
                    tabla.getDeclaraciones().add(declaracion.append(";"));
                    v.getCodigoGenerado().append("=").append(Tipos.valoreDefecto(v.getTipo())).insert(0, '(').append(')');
                }
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
        List<Simbolo> lista = Buscar.getCamino(v, Expresion.class, Igual.class);
        if (lista.isEmpty()) {
            lista = Buscar.getCamino(v, Expresion.class, Lista.class, ColParentesis.class, ExpColeccion.class, Igual.class);
        }
        int last = lista.size() - 1;
        return last > 0 && ((Igual) lista.get(last)).getIzquierda().equals(lista.get(last - 1));
    }

}
