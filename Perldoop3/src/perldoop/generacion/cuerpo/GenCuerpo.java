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
        int n = s.getSentencias().size();
        StringBuilder codigo = new StringBuilder(0); 
        if (n > 0 && s.getSentencias().get(n - 1).getCodigoGenerado() != null) {
            int tam = 0;
            for (Sentencia sts : s.getSentencias()) {
                tam += sts.getCodigoGenerado().length();
            }
            codigo = new StringBuilder(tam);
            for (Sentencia sts : s.getSentencias()) {
                codigo.append(sts.getCodigoGenerado());
            }
        }
        s.setCodigoGenerado(codigo);
    }

}
