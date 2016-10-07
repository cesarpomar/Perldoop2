package perldoop.generacion.cuerpo;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.sentencia.Sentencia;

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
        StringBuilder cuerpo = new StringBuilder(5000);
        for (Sentencia stc : s.getSentencias()) {
            cuerpo.append(stc.getCodigoGenerado());
        }
        s.setCodigoGenerado(cuerpo);
    }

}
