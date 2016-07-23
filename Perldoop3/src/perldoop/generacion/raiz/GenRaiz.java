package perldoop.generacion.raiz;

import java.io.File;
import perldoop.modelo.arbol.Raiz;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de paquetes
 *
 * @author César Pomar
 */
public class GenRaiz {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenRaiz(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Raiz s) {
        //Si no se decalraro un paquete le damos el nombre con el fichero
        if (tabla.getClase().getNombre() == null) {
            String nombre = new File(tabla.getGestorErrores().getFichero()).getName();
            if (nombre.endsWith(".pl") || nombre.endsWith(".pm")) {
                nombre = nombre.substring(0, nombre.length() - 3);
            }
            tabla.getClase().setNombre(nombre);
        }
        //Import libreria
        tabla.getClase().getImports().add("import perldoop.lib.*;");
    }
}
