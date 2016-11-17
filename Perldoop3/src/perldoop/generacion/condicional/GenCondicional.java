package perldoop.generacion.condicional;

import perldoop.generacion.bloque.GenBloque;
import perldoop.generacion.sentencia.GenSentencia;
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
        codigo.append(s.getId());
        codigo.append(s.getContexto().getLlaveI());
        codigo.append(s.getContexto().getCuerpo());
        codigo.append(s.getContexto().getLlaveD());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(CondicionalElsif s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("else if").append(s.getId().getComentario());
        codigo.append(s.getParentesisI());
        codigo.append(GenBloque.genExpresion(tabla, s.getExpresion()));
        codigo.append(s.getParentesisD());
        codigo.append(s.getContexto().getLlaveI());
        codigo.append(s.getContexto().getCuerpo());
        codigo.append(s.getContexto().getLlaveD());
        codigo.append(s.getBloqueElse());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(CondicionalNada s) {
        s.setCodigoGenerado(new StringBuilder());
    }


}
