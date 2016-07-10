package perldoop.generacion.variable;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.variable.*;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.acceso.AccesoArray;
import perldoop.modelo.arbol.acceso.AccesoMap;
import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.semantica.EntradaVariable;
import perldoop.util.Buscar;

/**
 * Clase generadora de variable
 *
 * @author César Pomar
 */
public class GenVariable {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenVariable(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(VarExistente s) {
        EntradaVariable e = tabla.getTablaSimbolos().buscarVariable(s.getVar().toString(), getContexto(s));
        s.setCodigoGenerado(new StringBuilder(e.getAlias()));
    }

    public void visitar(VarPaquete s) {
        EntradaVariable e = tabla.getTablaSimbolos().buscarVariable(s.getVar().toString(), getContexto(s));
        StringBuilder codigo = new StringBuilder(s.getPaquetes().getCodigoGenerado());
        codigo.append(".").append(e.getAlias());
        s.setCodigoGenerado(codigo);

    }

    public void visitar(VarMy s) {
        EntradaVariable e = tabla.getTablaSimbolos().buscarVariable(s.getVar().toString(), getContexto(s));
        StringBuilder declaracion = Tipos.declaracion(s.getTipo());
        e.setAlias(tabla.getGestorReservas().getAlias(e.getIdentificador(),e.isConflicto()));
        StringBuilder codigo;
        if(Buscar.getPadre(s, 2) instanceof StcLista){
            codigo = new StringBuilder(declaracion);
        }else{
            codigo = new StringBuilder(20);
        }
        codigo.append(" ").append(e.getAlias());
    }

    public void visitar(VarOur s) {
        EntradaVariable e = tabla.getTablaSimbolos().buscarVariable(s.getVar().toString(), getContexto(s));
        StringBuilder declaracion = Tipos.declaracion(s.getTipo());
        e.setAlias(tabla.getGestorReservas().getAlias(e.getIdentificador(),e.isConflicto()));
        tabla.getClase().getAtributos().add(declaracion.append(" ").append(e.getAlias()).append(";").toString());        
        s.setCodigoGenerado(new StringBuilder(e.getAlias()));
    }

    /**
     * Busca el contexto de una variable
     *
     * @param v Variable
     */
    private char getContexto(Variable v) {
        Simbolo uso = Buscar.getPadre(v, 1);
        if (uso instanceof AccesoArray) {
            return '@';
        } else if (uso instanceof AccesoMap) {
            return '%';
        } else {
            return v.getContexto().toString().charAt(0);
        }
    }

}
