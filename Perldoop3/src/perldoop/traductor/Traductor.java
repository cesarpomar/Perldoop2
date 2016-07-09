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
public final class Traductor implements Acciones {

    private List<Simbolo> simbolos;
    private int index;
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
        index = 0;
        this.simbolos = simbolos;
        this.semantica = semantica;
        this.generador = generador;
        this.opciones = opciones;
        this.semantica.setAcciones(this);
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
        for (; index < simbolos.size(); index++) {
            Simbolo s = simbolos.get(index);
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

    @Override
    public void reAnalizarDesdeDe(Simbolo s) {
        List<Simbolo> l = simbolos.subList(index, simbolos.size());
        int posicion = simbolos.indexOf(s);
        int distancia = 1;
        Simbolo padre = simbolos.get(index).getPadre();
        while (padre != null && l.indexOf(padre) + index < posicion) {
            distancia++;
            padre = padre.getPadre();
        }
        for (int i = index; i < posicion - distancia + 1; i++) {
            simbolos.set(i, simbolos.set(i + distancia, simbolos.get(i)));
        }
        index--;
    }

}
