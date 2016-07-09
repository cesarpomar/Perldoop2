package perldoop.generacion.constante;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.constante.CadenaComando;
import perldoop.modelo.arbol.constante.CadenaDoble;
import perldoop.modelo.arbol.constante.CadenaSimple;
import perldoop.modelo.arbol.constante.Decimal;
import perldoop.modelo.arbol.constante.Entero;

/**
 * Clase generadora de constante
 *
 * @author César Pomar
 */
public class GenConstante {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenConstante(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Entero s) {
        s.setCodigoGenerado(new StringBuilder(s.getEntero().toString()));
    }

    public void visitar(Decimal s) {
        s.setCodigoGenerado(new StringBuilder(s.getDecimal().toString()));
    }

    public void visitar(CadenaSimple s) {
        StringBuilder codigo = new StringBuilder(s.getCadenaSimple().toString());
        codigo.setCharAt(0, '"');
        codigo.setCharAt(codigo.length() - 1, '"');
        s.setCodigoGenerado(codigo);
    }

    public void visitar(CadenaDoble s) {
        StringBuilder codigo = new StringBuilder(s.getCadenaDoble().toString());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(CadenaComando s) {
        StringBuilder codigo = new StringBuilder(s.getCadenaComando().toString());
        codigo.setCharAt(0, '"');
        codigo.setCharAt(codigo.length() - 1, '"');
        s.setCodigoGenerado(codigo);
    }
}
