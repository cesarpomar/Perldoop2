package perldoop.interfaz;

import perldoop.controlador.Controlador;
import perldoop.error.GestorErrores;
import perldoop.internacionalizacion.Errores;
import perldoop.jar.LibJar;
import perldoop.modelo.Opciones;

/**
 * Clase para gestionar el funcionamiento de la herramienta
 *
 * @author César Pomar
 */
public final class Perldoop {

    /**
     * Punto de inicio de la aplicacion
     *
     * @param args Argumentos de ejecución
     */
    public static void main(String[] args) {
        Consola consola = new Consola(args);
        consola.parse();
        Opciones opciones = consola.getOpciones();
        Controlador controlador = new Controlador(opciones, consola.getFicheros());
        controlador.iniciar();
        //Genera la libreria para ejecución
        if (opciones.isLibreria()) {
            try {
                LibJar.buildLib(opciones.getDirectorioSalida());
            } catch (Exception ex) {
                new GestorErrores(opciones.getDirectorioSalida().getPath(), opciones).error(Errores.ERROR_LIBRERIA);
            }
        }

    }
}
