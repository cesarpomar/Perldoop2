package perldoop.modelo.generacion;

import java.util.ArrayList;
import java.util.List;
import perldoop.modelo.Opciones;
import perldoop.modelo.semantica.TablaSimbolos;

/**
 * Clase que contiene los datos relativos a la generación de código
 *
 * @author César Pomar
 */
public final class TablaGenerador {

    private TablaSimbolos tablaSimbolos;
    private GestorReservas gestorReservas;
    private Opciones opciones;
    private String fichero;
    private ClaseJava clase;
    private List<Declaracion> declaraciones;

    /**
     * Contruye la tabla del generador
     *
     * @param tablaSimbolos Tabla de símbolos
     * @param opciones Opciones
     * @param fichero Fichero
     */
    public TablaGenerador(TablaSimbolos tablaSimbolos, Opciones opciones, String fichero) {
        this.tablaSimbolos = tablaSimbolos;
        this.gestorReservas = new GestorReservas();
        this.opciones = opciones;
        this.fichero = fichero;
        declaraciones = new ArrayList<>(10);
        clase = new ClaseJava();
    }

    /**
     * Obtiene la tabla de símbolos
     *
     * @return Tabla de símbolos
     */
    public TablaSimbolos getTablaSimbolos() {
        return tablaSimbolos;
    }

    /**
     * Establece la tabla de símbolos
     *
     * @param tablaSimbolos Tabla de símbolos
     */
    public void setTablaSimbolos(TablaSimbolos tablaSimbolos) {
        this.tablaSimbolos = tablaSimbolos;
    }

    /**
     * Obtiene las opciones
     *
     * @return Opciones
     */
    public Opciones getOpciones() {
        return opciones;
    }

    /**
     * Establece las opciones
     *
     * @param opciones Opciones
     */
    public void setOpciones(Opciones opciones) {
        this.opciones = opciones;
    }

    /**
     * Obtiene el fichero Perl
     *
     * @return Fichero Perl
     */
    public String getFichero() {
        return fichero;
    }

    /**
     * Establece el fichero Perl
     *
     * @param fichero Fichero Perl
     */
    public void setFichero(String fichero) {
        this.fichero = fichero;
    }

    /**
     * Obtiene la clase
     *
     * @return Clase
     */
    public ClaseJava getClase() {
        return clase;
    }

    /**
     * Establece la clase
     *
     * @param clase Clase
     */
    public void setClase(ClaseJava clase) {
        this.clase = clase;
    }

    /**
     * Obtiene el gestor de reservas
     *
     * @return Gestor de reservas
     */
    public GestorReservas getGestorReservas() {
        return gestorReservas;
    }

    /**
     * Obtiene las declaraciones
     *
     * @return Declaraciones
     */
    public List<Declaracion> getDeclaraciones() {
        return declaraciones;
    }

    /**
     * Establece las declaraciones
     *
     * @param declaraciones Declaraciones
     */
    public void setDeclaraciones(List<Declaracion> declaraciones) {
        this.declaraciones = declaraciones;
    }

}
