package perldoop.generacion.sentencia;

import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.sentencia.StcBloque;
import perldoop.modelo.arbol.sentencia.StcComentario;
import perldoop.modelo.arbol.sentencia.StcDeclaracion;
import perldoop.modelo.arbol.sentencia.StcFlujo;
import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.arbol.sentencia.StcPaquete;

/**
 * Clase generadora de sentencia
 *
 * @author César Pomar
 */
public class GenSentencia {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenSentencia(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(StcLista s) {
    }

    public void visitar(StcBloque s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(StcFlujo s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(StcPaquete s) {
        tabla.getClase().setNombre(s.getId().toString());
        for (Terminal p : s.getPaquetes().getIdentificadores()) {
            tabla.getClase().getPaquetes().add(p.toString());
        }
    }

    public void visitar(StcComentario s) {
        
    }

    public void visitar(StcDeclaracion s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
