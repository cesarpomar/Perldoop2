package perldoop.generacion.cadenatexto;

import java.util.Iterator;
import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.cadenatexto.CadenaTexto;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de constante
 *
 * @author César Pomar
 */
public class GenCadenaTexto {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenCadenaTexto(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(CadenaTexto s) {
        StringBuilder codigo = new StringBuilder(100);
        List<Simbolo> lista = s.getElementos();
        if (!lista.isEmpty() && !(lista.get(0) instanceof Terminal) && !lista.get(0).getTipo().isString()) {
            codigo.append("\"\"+");
        }
        Iterator<Simbolo> it = lista.iterator();
        while (it.hasNext()) {
            Simbolo e = it.next();
            if (e instanceof Terminal) {
                codigo.append('"').append(e).append('"');
            } else {
                codigo.append(e);
            }
            if (it.hasNext()) {
                codigo.append("+");
            }
        }
        s.setCodigoGenerado(codigo);
    }

}
