package perldoop.generacion.bloque;

import java.util.Iterator;
import perldoop.generacion.util.Casting;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.SimboloAux;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.bloque.*;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.variable.VarMy;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Tipo;

/**
 * Clase generadora de bloque
 *
 * @author César Pomar
 */
public class GenBloque {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenBloque(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(BloqueWhile s) {
        StringBuilder codigo = new StringBuilder(1000);
        genDeclaraciones(codigo);
        codigo.append(s.getId());
        codigo.append(s.getParentesisI());
        codigo.append(genExpresion(s.getExpresion()));
        codigo.append(s.getParentesisD());
        codigo.append(s.getContexto().getLlaveI());
        codigo.append(s.getContexto().getCuerpo());
        codigo.append(s.getContexto().getLlaveD());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(BloqueUntil s) {
        StringBuilder codigo = new StringBuilder(1000);
        genDeclaraciones(codigo);
        codigo.append("while").append(s.getId().getComentario());
        codigo.append(s.getParentesisI());
        codigo.append("!(");
        codigo.append(genExpresion(s.getExpresion()));
        codigo.append(")");
        codigo.append(s.getParentesisD());
        codigo.append(s.getContexto().getLlaveI());
        codigo.append(s.getContexto().getCuerpo());
        codigo.append(s.getContexto().getLlaveD());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(BloqueDoWhile s) {
        StringBuilder codigo = new StringBuilder(1000);
        genDeclaraciones(codigo);
        codigo.append(s.getId());
        codigo.append(s.getContexto().getLlaveI());
        codigo.append(s.getContexto().getCuerpo());
        codigo.append(s.getContexto().getLlaveD());
        codigo.append(s.getIdWhile());
        codigo.append(s.getParentesisI());
        codigo.append(genExpresion(s.getExpresion()));
        codigo.append(s.getParentesisD());
        codigo.append(s.getPuntoComa());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(BloqueDoUntil s) {
        StringBuilder codigo = new StringBuilder(1000);
        genDeclaraciones(codigo);
        codigo.append(s.getId());
        codigo.append(s.getContexto().getLlaveI());
        codigo.append(s.getContexto().getCuerpo());
        codigo.append(s.getContexto().getLlaveD());
        codigo.append("while").append(s.getIdUntil().getComentario());
        codigo.append(s.getParentesisI());
        codigo.append("!(");
        codigo.append(genExpresion(s.getExpresion()));
        codigo.append(")");
        codigo.append(s.getParentesisD());
        codigo.append(s.getPuntoComa());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(BloqueFor s) {
        StringBuilder codigo = new StringBuilder(1000);
        genDeclaraciones(codigo);
        codigo.append(s.getId());
        codigo.append(s.getParentesisI());
        Iterator<Terminal> itt;
        Iterator<Expresion> itexp;
        //Primer campo
        itt = s.getLista1().getSeparadores().iterator();
        itexp = s.getLista1().getExpresiones().iterator();
        while (itexp.hasNext()) {
            codigo.append(itexp.next());
            if (itexp.hasNext()) {
                codigo.append(",");
            }
            if (itt.hasNext()) {
                codigo.append(itt.next().getComentario());
            }
        }
        codigo.append(s.getPuntoComa1());
        //Segundo campo
        itt = s.getLista2().getSeparadores().iterator();
        itexp = s.getLista2().getExpresiones().iterator();
        while (itexp.hasNext()) {
            codigo.append(Casting.toBoolean(itexp.next()));
            if (itexp.hasNext()) {
                codigo.append("&&");
            }
            if (itt.hasNext()) {
                codigo.append(itt.next().getComentario());
            }
        }
        codigo.append(s.getPuntoComa2());
        //Tercer campo
        itt = s.getLista3().getSeparadores().iterator();
        itexp = s.getLista3().getExpresiones().iterator();
        while (itexp.hasNext()) {
            codigo.append(itexp.next());
            if (itexp.hasNext()) {
                codigo.append(",");
            }
            if (itt.hasNext()) {
                codigo.append(itt.next().getComentario());
            }
        }
        codigo.append(s.getParentesisD());
        codigo.append(s.getContexto().getLlaveI());
        codigo.append(s.getContexto().getCuerpo());
        codigo.append(s.getContexto().getLlaveD());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(BloqueForeachVar s) {
        StringBuilder codigo = new StringBuilder(1000);
        StringBuilder asignacion = new StringBuilder(0);
        codigo.append(s.getId());
        codigo.append("(");
        if (s.getVariable() instanceof VarMy) {
            codigo.append(s.getVariable());
        } else {
            SimboloAux aux = new SimboloAux(s.getColeccion().getTipo().getSubtipo(1));
            String var = tabla.getGestorReservas().getAux();
            aux.setCodigoGenerado(new StringBuilder(var));
            codigo.append(Tipos.declaracion(aux.getTipo()));
            codigo.append(" ").append(var);
            asignacion = new StringBuilder(100);
            asignacion.append(s.getVariable()).append("=");
            asignacion.append(Casting.casting(aux, s.getVariable().getTipo())).append(";");
        }
        codigo.append(":");
        if (s.getColeccion().getTipo().isMap()) {
            codigo.append(Casting.casting(s.getColeccion(), s.getTipo().getSubtipo(1).add(0, Tipo.LIST)));
        } else {
            codigo.append(s.getColeccion());
        }
        codigo.append(")");
        codigo.append(s.getContexto().getLlaveI());
        codigo.append(asignacion);
        codigo.append(s.getContexto().getCuerpo());
        codigo.append(s.getContexto().getLlaveD());
        s.setCodigoGenerado(codigo);
        s.setCodigoGenerado(codigo);
    }

    public void visitar(BloqueForeach s) {
        StringBuilder codigo = new StringBuilder(1000);
        genDeclaraciones(codigo);
        String aux = tabla.getGestorReservas().getAux();
        Tipo t = new Tipo(Tipo.INTEGER);
        codigo.append(s.getId());
        codigo.append("(");
        codigo.append(Tipos.declaracion(t)).append(" ").append(aux).append(";");
        codigo.append(aux).append("<").append(Casting.casting(s.getColeccion(), t)).append(";");
        codigo.append(aux).append("++");
        codigo.append(")");
        codigo.append(s.getContexto().getLlaveI());
        codigo.append(s.getContexto().getCuerpo());
        codigo.append(s.getContexto().getLlaveD());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(BloqueIf s) {
        StringBuilder codigo = new StringBuilder(1000);
        genDeclaraciones(codigo);
        codigo.append(s.getId());
        codigo.append(s.getParentesisI());
        codigo.append(genExpresion(s.getExpresion()));
        codigo.append(s.getParentesisD());
        codigo.append(s.getContexto().getLlaveI());
        codigo.append(s.getContexto().getCuerpo());
        codigo.append(s.getContexto().getLlaveD());
        codigo.append(s.getBloqueElse());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(BloqueUnless s) {
        StringBuilder codigo = new StringBuilder(1000);
        genDeclaraciones(codigo);
        codigo.append("if").append(s.getId().getComentario());
        codigo.append(s.getParentesisI());
        codigo.append("!(");
        codigo.append(genExpresion(s.getExpresion()));
        codigo.append(")");
        codigo.append(s.getParentesisD());
        codigo.append(s.getContexto().getLlaveI());
        codigo.append(s.getContexto().getCuerpo());
        codigo.append(s.getContexto().getLlaveD());
        codigo.append(s.getBloqueElse());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(BloqueVacio s) {
        //TODO actualizar
        StringBuilder codigo = new StringBuilder(1000);
        codigo.append(s.getContexto().getLlaveI());
        codigo.append(s.getContexto().getCuerpo());
        codigo.append(s.getContexto().getLlaveD());
        s.setCodigoGenerado(codigo);
    }

    /**
     * Añade las declaraciones de la expresion si las hay
     *
     * @param codigo Codigo bloque
     */
    private void genDeclaraciones(StringBuilder codigo) {
        for (StringBuilder dec : tabla.getDeclaraciones()) {
            codigo.append(dec);
        }
        tabla.getDeclaraciones().clear();
    }

    /**
     * Genera la expresion que evalua el bloque de control para su ejecucion
     *
     * @param exp Expresion
     * @return Codigo expresion
     */
    private StringBuilder genExpresion(Expresion exp) {
        return genExpresion(tabla, exp);
    }

    /**
     * Genera la expresion que evalua el bloque de control para su ejecucion
     *
     * @param tabla Tabla
     * @param exp Expresion
     * @return Codigo expresion
     */
    public static StringBuilder genExpresion(TablaGenerador tabla, Expresion exp) {
        if (!tabla.getOpciones().isOptNulos()) {
            return Casting.castingNotNull(exp, new Tipo(Tipo.BOOLEAN));
        }
        return exp.getCodigoGenerado();
    }

}
