package perldoop.generacion.cuerpo;

import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.sentencia.Sentencia;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de cuerpo
 *
 * @author César Pomar
 */
public class GenCuerpo {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenCuerpo(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Cuerpo s) {
        StringBuilder codigo = new StringBuilder(5000);
        for (Sentencia stc : s.getSentencias()) {
            codigo.append(stc.getCodigoGenerado());
        }
        s.setCodigoGenerado(codigo);
    }

}
