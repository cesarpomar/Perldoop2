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
     * @param tabla Tabla
     */
    public GenRaiz(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Raiz s) {
        //Si no se declaro un paquete le damos el nombre con el fichero
        if (tabla.getClase().getNombre() == null) {
            String nombre = new File(tabla.getGestorErrores().getFichero().trim()).getName();
            nombre = nombre.replaceAll("[^a-zA-Z0-9]", "_");
            if (nombre.matches("^[0-9].*")) {
                nombre = "_" + nombre;
            }
            tabla.getClase().setNombre(nombre);
        }
        //Import libreria
        tabla.getClase().getImports().add("import perldoop.lib.*;");
    }
}
