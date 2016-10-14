package perldoop.generacion.condicional;

import perldoop.generacion.util.Casting;
import perldoop.modelo.arbol.condicional.*;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Clase generadora de condicional
 *
 * @author César Pomar
 */
public class GenCondicional {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenCondicional(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(CondicionalElse s) {
        StringBuilder codigo = new StringBuilder(100);
        genDeclaraciones(codigo);
        codigo.append(s.getId());
        codigo.append(s.getLlaveI());
        codigo.append(s.getCuerpo());
        codigo.append(s.getLlaveD());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(CondicionalElsif s) {
        StringBuilder codigo = new StringBuilder(100);
        genDeclaraciones(codigo);
        codigo.append("else if").append(s.getId().getComentario());
        codigo.append(s.getParentesisI());
        codigo.append(genExpresion(s.getExpresion()));
        codigo.append(s.getParentesisD());
        codigo.append(s.getLlaveI());
        codigo.append(s.getCuerpo());
        codigo.append(s.getLlaveD());
        codigo.append(s.getBloqueElse());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(CondicionalNada s) {
        s.setCodigoGenerado(new StringBuilder());
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
