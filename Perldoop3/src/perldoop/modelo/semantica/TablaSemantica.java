package perldoop.modelo.semantica;

import perldoop.error.GestorErrores;
import perldoop.modelo.Opciones;
import perldoop.traductor.Acciones;

/**
 * Clase que contiene los datos relativos a la generación de código
 *
 * @author César Pomar
 */
public class TablaSemantica {

    private TablaSimbolos tablaSimbolos;
    private GestorErrores gestorErrores;
    private Opciones opciones;
    private Acciones acciones;
    private ClaseAttr claseAttr;

    /**
     * Contruye la tabla del generador
     *
     * @param tablaSimbolos Tabla de símbolos
     * @param opciones opciones
     * @param gestionErrores Gestión de errores
     */
    public TablaSemantica(TablaSimbolos tablaSimbolos, Opciones opciones, GestorErrores gestionErrores) {
        this.tablaSimbolos = tablaSimbolos;
        this.opciones = opciones;
        this.gestorErrores = gestionErrores;
        claseAttr = new ClaseAttr();
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
     * Obtiene las acciones
     *
     * @return Acciones
     */
    public Acciones getAcciones() {
        return acciones;
    }

    /**
     * Establece las acciones
     *
     * @param acciones Acciones
     */
    public void setAcciones(Acciones acciones) {
        this.acciones = acciones;
    }

    /**
     * Obtiene los atributos de la clase
     *
     * @return Atributos de la clase
     */
    public ClaseAttr getClaseAttr() {
        return claseAttr;
    }

}
