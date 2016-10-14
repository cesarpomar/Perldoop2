package perldoop.generacion.fuente;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.fuente.Fuente;
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
        StringBuilder global = new StringBuilder(10000);
        global.append("private static void global(){");
        int tam = global.length();
        for (Simbolo codigo : s.getCodigoFuente()) {
            if (codigo instanceof Cuerpo) {
                //Codigo global
                global.append(codigo.getCodigoGenerado());
            } else {
                //Funciones
                tabla.getClase().getFunciones().add(codigo.getCodigoGenerado());
            }
        }
        if (tam < global.length()) {
            global.append("}");
            tabla.getClase().getFunciones().add(global);
            tabla.getClase().getFunciones().add(new StringBuilder("static{global();}"));
        }
    }
}
