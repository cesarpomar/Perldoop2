package perldoop.semantico;

import java.util.List;
import java.util.Map;
import perldoop.error.GestorErrores;
import perldoop.semantico.excepciones.ExcepcionSemantica;
import perldoop.modelo.Opciones;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.sentencia.Sentencia;
import perldoop.modelo.semantico.Paquete;
import perldoop.modelo.semantico.TablaSimbolos;

/**
 * Clase que verifica la semanca del arbol de simbolos.
 *
 * @author César Pomar
 */
public final class AnalizadorSemantico {

    private List<Simbolo> simbolos;
    private Opciones opciones;
    private GestorErrores gestorErrores;
    private TablaSimbolos tablaSimbolos;
    private int errores;

    /**
     * Construye el analizador semantico
     *
     * @param simbolos Simbolos
     * @param opciones Opciones
     * @param gestorErrores Gestor de errores
     * @param paquetes Paquetes
     */
    public AnalizadorSemantico(List<Simbolo> simbolos, Opciones opciones, GestorErrores gestorErrores, Map<String,Paquete> paquetes) {
        errores = 0;
        this.simbolos = simbolos;
        this.opciones = opciones;
        this.gestorErrores = gestorErrores;
        this.tablaSimbolos = new TablaSimbolos(paquetes);
    }

    /**
     * Establece el gestor de errores
     *
     * @param gestorErrores Gestor de errores
     */
    public void setGestorErrores(GestorErrores gestorErrores) {
        this.gestorErrores = gestorErrores;
    }

    /**
     * Obtiene el gestor de errores
     *
     * @return Gestor de errores
     */
    public GestorErrores getGestorErrores() {
        return gestorErrores;
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
     * Obtiene el número de errores, si no hay errores el analisis se ha
     * realizado correctamente.
     *
     * @return Número de errores
     */
    public int getErrores() {
        return errores;
    }

    /**
     * Inicia la verificación.
     */
    public void analizar() {
        Semantica semantica = new Semantica();
        boolean error = false;
        for (Simbolo s : simbolos) {
            if (!error) {
                try {
                    s.aceptar(semantica);
                } catch (ExcepcionSemantica ex) {
                    error = true;
                    errores++;
                }
            } else if (s instanceof Sentencia) {
                error = false;
            }
        }
    }

}
