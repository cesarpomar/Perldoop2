package perldoop.traductor;

import java.util.ArrayList;
import java.util.List;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.generacion.Generador;
import perldoop.modelo.Opciones;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.arbol.sentencia.Sentencia;
import perldoop.semantica.Semantica;

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
    private boolean generar;
    private boolean detener;//Detiene el traductor
    private boolean reanudable;//Comprueba si el traductor puede detenerse

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
        reanudable = true;
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
     * Obtiene los simbolos
     *
     * @return Lista de simbolos
     */
    public List<Simbolo> getSimbolos() {
        return simbolos;
    }

    /**
     * Obtiene la semantica
     *
     * @return Semantica
     */
    public Semantica getSemantica() {
        return semantica;
    }

    /**
     * Obtiene el generador
     *
     * @return Generador
     */
    public Generador getGenerador() {
        return generador;
    }

    /**
     * Comprueba si el traductor ha finalizado la traducción
     *
     * @return Traduccion finalizada
     */
    public boolean isFinalizado() {
        return index == simbolos.size();
    }

    /**
     * Comprueba si el traductor puede ser detenido
     *
     * @return El traductor puede ser detenido
     */
    public boolean isReanudable() {
        return reanudable;
    }

    /**
     * Establece si el traductor puede ser detenido
     *
     * @param reanudable El traductor puede ser detenido
     */
    public void setReanudable(boolean reanudable) {
        this.reanudable = reanudable;
    }

    /**
     * Obtiene el progreso del traductor, calculado como el numero de simbolos analizados
     *
     * @return Progreso
     */
    public int getProgreso() {
        return index;
    }

    /**
     * Inicia la verificacion y generacion del codigo.
     *
     * @return Número de errores
     */
    public int traducir() {
        boolean error = false;
        detener = false;
        for (; !detener && index < simbolos.size(); index++) {
            if (!error) {
                try {
                    analizar(simbolos.get(index));
                } catch (ExcepcionSemantica ex) {
                    error = true;
                    errores++;
                }
            } else if (simbolos.get(index) instanceof Sentencia || simbolos.get(index) instanceof Bloque) {
                error = false;
            } else if (simbolos.get(index) instanceof AbrirBloque) {//Asegurar que se abren los contextos
                simbolos.get(index).aceptar(semantica);
            }
        }
        return errores;
    }

    /**
     * Analiza un simbolo.
     *
     * @param s Simbolo
     */
    private void analizar(Simbolo s) throws ExcepcionSemantica {
        generar = true;
        s.aceptar(semantica);
        if (generar && errores == 0) {
            s.aceptar(generador);
        }
    }

    @Override
    public void reAnalizarDespuesDe(Simbolo s) {
        List<Simbolo> l = simbolos.subList(index, simbolos.size());
        List<Simbolo> dependencias = new ArrayList<>(10);
        int posicion = l.indexOf(s);
        Simbolo padre = l.get(0);
        //Calculamos las dependicias
        while (padre != null && l.indexOf(padre) <= posicion) {
            dependencias.add(padre);
            padre = padre.getPadre();
        }
        //Vamos moviendo todas las dependencias
        for (Simbolo actual : dependencias) {
            for (int i = l.indexOf(actual); i < posicion; i++) {
                l.set(i, l.set(i + 1, l.get(i)));
            }
        }
        index--;
        generar = false;
    }

    @Override
    public void saltarGenerador() {
        generar = false;
    }

    @Override
    public boolean detenerTraductor() {
        if (reanudable) {
            detener = true;
            generar = false;
            index--;
        }
        return reanudable;
    }

}
