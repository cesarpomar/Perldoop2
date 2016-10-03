package perldoop.generacion.variable;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.variable.*;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Terminal;
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
        if(e.getTipo().isArray()){
            codigo.append(".length - 1)");
        }else{
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
        if(e.getTipo().isArray()){
            codigo.append(".length - 1)");
        }else{
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
        StringBuilder declaracion = new StringBuilder(100);
        if (publica || tabla.getTablaSimbolos().getBloques() == 1) {
            if (publica) {
                declaracion.append("public ");
            } else {
                declaracion.append("private ");
            }
            declaracion.append("static ").append(Tipos.declaracion(v.getTipo())).append(dec.getComentario()).append(" ");
            declaracion.append(e.getAlias()).append(";");
            tabla.getClase().getAtributos().add(declaracion.toString());
            v.setCodigoGenerado(new StringBuilder(e.getAlias()).append(v.getVar().getComentario()));
        } else {
            declaracion.append(Tipos.declaracion(v.getTipo())).append(" ");
            declaracion.append(e.getAlias());
            declaracion.append(v.getVar().getComentario());
            v.setCodigoGenerado(declaracion);
        }

    }

}
