package perldoop.traductor;

import java.util.List;
import perldoop.generacion.Generador;
import perldoop.modelo.Opciones;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.sentencia.Sentencia;
import perldoop.semantica.Semantica;
import perldoop.excepciones.ExcepcionSemantica;

/**
 * Clase que verifica la semanca del arbol de simbolos.
 *
 * @author César Pomar
 */
public final class Traductor {

    private List<Simbolo> simbolos;
    private Opciones opciones;
    private Semantica semantica;
    private Generador generador;
    private int errores;

    /**
     * Construye el traductor
     *
     * @param simbolos Simbolos
     * @param semantica Semantica
     * @param generador Generador
     * @param opciones Opciones
     */
    public Traductor(List<Simbolo> simbolos, Semantica semantica, Generador generador, Opciones opciones) {
        errores = 0;
        this.simbolos = simbolos;
        this.semantica = semantica;
        this.generador = generador;
        this.opciones = opciones;
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
     * Inicia la verificacion y generacion del codigo.
     *
     * @return Número de errores
     */
    public int traducir() {
        boolean error = false;
        for (Simbolo s : simbolos) {
            if (!error) {
                try {
                    s.aceptar(semantica);
                    if (errores == 0) {
                        s.aceptar(generador);
                    }
                } catch (ExcepcionSemantica ex) {
                    error = true;
                    errores++;
                }
            } else if (s instanceof Sentencia) {
                error = false;
            }
        }
        return errores;
    }

}
