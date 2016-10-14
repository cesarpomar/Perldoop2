package perldoop.generacion.flujo;

import perldoop.modelo.arbol.flujo.Last;
import perldoop.modelo.arbol.flujo.Next;
import perldoop.modelo.arbol.flujo.Return;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de flujo
 *
 * @author César Pomar
 */
public class GenFlujo {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenFlujo(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Next s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getNext());
        codigo.append(s.getPuntoComa());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(Last s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("continue").append(s.getLast().getComentario());
        codigo.append(s.getPuntoComa());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(Return s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getId()).append(" ");
        if (s.getExpresion() != null) {
            codigo.append(s.getExpresion());
        }
        codigo.append(s.getPuntoComa());
        s.setCodigoGenerado(codigo);
    }

}
