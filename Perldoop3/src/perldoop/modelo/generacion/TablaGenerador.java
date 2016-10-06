package perldoop.modelo.generacion;

import java.util.ArrayList;
import java.util.List;
import perldoop.error.GestorErrores;
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
    private GestorErrores gestorErrores;
    private ClaseJava clase;
    private List<StringBuilder> declaraciones;
    private StringBuilder codigoGlobal;
    private StringBuilder codigoMain;

    /**
     * Contruye la tabla del generador
     *
     * @param tablaSimbolos Tabla de símbolos
     * @param opciones Opciones
     * @param gestorErrores Gestor de errores
     */
    public TablaGenerador(TablaSimbolos tablaSimbolos, Opciones opciones, GestorErrores gestorErrores) {
        this.tablaSimbolos = tablaSimbolos;
        this.gestorReservas = new GestorReservas();
        this.opciones = opciones;
        this.gestorErrores = gestorErrores;
        declaraciones = new ArrayList<>(10);
        codigoGlobal = new StringBuilder(10000);
        codigoMain = new StringBuilder(10000);
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
     * Obtiene el gestor de errores
     *
     * @return Gestión de errores
     */
    public GestorErrores getGestorErrores() {
        return gestorErrores;
    }

    /**
     * Establce el gestor de errores
     *
     * @param gestorErrores Gestor de errores
     */
    public void setGestorErrores(GestorErrores gestorErrores) {
        this.gestorErrores = gestorErrores;
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
    public List<StringBuilder> getDeclaraciones() {
        return declaraciones;
    }

    /**
     * Establece las declaraciones
     *
     * @param declaraciones Declaraciones
     */
    public void setDeclaraciones(List<StringBuilder> declaraciones) {
        this.declaraciones = declaraciones;
    }

    /**
     * Obtiene el código global
     *
     * @return Código global
     */
    public StringBuilder getCodigoGlobal() {
        return codigoGlobal;
    }

    /**
     * Establece el código global
     *
     * @param codigoGlobal Código global
     */
    public void setCodigoGlobal(StringBuilder codigoGlobal) {
        this.codigoGlobal = codigoGlobal;
    }

    /**
     * Obtiene el código main
     *
     * @return Código main
     */
    public StringBuilder getCodigoMain() {
        return codigoMain;
    }

    /**
     * Establece el código main
     *
     * @param codigoMain Código main
     */
    public void setCodigoMain(StringBuilder codigoMain) {
        this.codigoMain = codigoMain;
    }

}
