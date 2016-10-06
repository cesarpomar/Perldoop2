package perldoop.generacion.fuente;

import perldoop.modelo.arbol.fuente.Fuente;
import perldoop.modelo.arbol.sentencia.Sentencia;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de fuente(Sentencias globales)
 *
 * @author César Pomar
 */
public class GenFuente {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenFuente(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Fuente s) {
        for(Sentencia stc:s.getCuerpo().getSentencias()){
            tabla.getCodigoGlobal().append(stc.getCodigoGenerado());
        }
    }
}
