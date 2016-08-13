package perldoop.generacion.paquetes;

import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.paquete.Paquetes;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de paquetes
 *
 * @author César Pomar
 */
public class GenPaquetes {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenPaquetes(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Paquetes s) {
        StringBuilder codigo = new StringBuilder(100);
        for (Terminal t : s.getIdentificadores()) {
            codigo.append(t.getCodigoGenerado()).append(".");
        }
        s.setCodigoGenerado(codigo);
    }
}
