package perldoop.generacion.bloque;

import perldoop.generacion.util.Casting;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.bloque.*;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

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
        StringBuilder codigo = new StringBuilder(100);
        genDeclaraciones(codigo);
        codigo.append(s.getId());
        codigo.append(s.getParentesisI());
        codigo.append(genExpresion(s.getExpresion()));
        codigo.append(s.getParentesisD());
        codigo.append(s.getLlaveI());
        codigo.append(s.getCuerpo());
        codigo.append(s.getLlaveD());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(BloqueUntil s) {
        StringBuilder codigo = new StringBuilder(100);
        genDeclaraciones(codigo);
        codigo.append("while").append(s.getId().getComentario());
        codigo.append(s.getParentesisI());
        codigo.append("!(");
        codigo.append(genExpresion(s.getExpresion()));
        codigo.append(")");
        codigo.append(s.getParentesisD());
        codigo.append(s.getLlaveI());
        codigo.append(s.getCuerpo());
        codigo.append(s.getLlaveD());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(BloqueDoWhile s) {
        StringBuilder codigo = new StringBuilder(100);
        genDeclaraciones(codigo);
        codigo.append(s.getId());
        codigo.append(s.getLlaveI());
        codigo.append(s.getCuerpo());
        codigo.append(s.getLlaveD());
        codigo.append(s.getIdWhile());
        codigo.append(s.getParentesisI());
        codigo.append(genExpresion(s.getExpresion()));
        codigo.append(s.getParentesisD());
        codigo.append(s.getPuntoComa());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(BloqueDoUntil s) {
        StringBuilder codigo = new StringBuilder(100);
        genDeclaraciones(codigo);
        codigo.append(s.getId());
        codigo.append(s.getLlaveI());
        codigo.append(s.getCuerpo());
        codigo.append(s.getLlaveD());
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
        //TODO
    }

    public void visitar(BloqueForeachVar s) {
        //TODO
    }

    public void visitar(BloqueForeach s) {
        //TODO
    }

    public void visitar(BloqueIf s) {
        StringBuilder codigo = new StringBuilder(100);
        genDeclaraciones(codigo);
        codigo.append(s.getId());
        codigo.append(s.getParentesisI());
        codigo.append(genExpresion(s.getExpresion()));
        codigo.append(s.getParentesisD());
        codigo.append(s.getLlaveI());
        codigo.append(s.getCuerpo());
        codigo.append(s.getLlaveD());
        codigo.append(s.getBloqueElse());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(BloqueUnless s) {
        StringBuilder codigo = new StringBuilder(100);
        genDeclaraciones(codigo);
        codigo.append("if").append(s.getId().getComentario());
        codigo.append(s.getParentesisI());
        codigo.append("!(");
        codigo.append(genExpresion(s.getExpresion()));
        codigo.append(")");
        codigo.append(s.getParentesisD());
        codigo.append(s.getLlaveI());
        codigo.append(s.getCuerpo());
        codigo.append(s.getLlaveD());
        codigo.append(s.getBloqueElse());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(BloqueVacio s) {
        //TODO actualizar
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getLlaveI());
        codigo.append(s.getCuerpo());
        codigo.append(s.getLlaveD());        
        s.setCodigoGenerado(codigo);
    }

    /**
     * Añade las declaraciones de la expresion si las hay
     *
     * @param codigo Codigo bloque
     */
    public void genDeclaraciones(StringBuilder codigo) {
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
    public StringBuilder genExpresion(Expresion exp) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(Casting.casting(exp, new Tipo(Tipo.BOOLEAN)));
        if (!Buscar.isNotNull(exp)) {
            codigo.insert(0, "Pd.checkNull(").append(")");
        }
        return codigo;
    }

}
