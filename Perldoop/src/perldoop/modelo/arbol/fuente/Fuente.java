package perldoop.modelo.arbol.fuente;

import java.util.ArrayList;
import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.funciondef.FuncionDef;

/**
 * Clase que representa la reduccion -&gt; fuente : cuerpo masFuente <br>
 * masFuente :<br>
 * | funcionDef fuente
 *
 * @author César Pomar
 */
public final class Fuente extends Simbolo {

    private List<Simbolo> elementos;
    private List<FuncionDef> funciones;
    private List<Cuerpo> cuerpos;

    /**
     * Único contructor de la clase
     */
    public Fuente() {
        funciones = new ArrayList<>(100);
        cuerpos = new ArrayList<>(100);
        elementos = new ArrayList<>(100);
    }

    /**
     * Añade una funcion
     *
     * @param funcion Funcion
     * @return Esta instancia
     */
    public Fuente addFuncion(FuncionDef funcion) {
        elementos.add(funcion);
        funciones.add(funcion);
        funcion.setPadre(this);
        return this;
    }

    /**
     * Realiza la misma acción que invocar f.addFuncion(codigo)
     *
     * @param f Fuente
     * @param funcion Funcion
     * @return Fuente f
     */
    public static Fuente addFuncion(Fuente f, FuncionDef funcion) {
        return f.addFuncion(funcion);
    }

    /**
     * Añade un cuerpo
     *
     * @param cuerpo Cuerpo
     * @return Esta instancia
     */
    public Fuente addCuerpo(Cuerpo cuerpo) {
        elementos.add(cuerpo);
        cuerpos.add(cuerpo);
        cuerpo.setPadre(this);
        return this;
    }

    /**
     * Realiza la misma acción que invocar f.addCuerpo(codigo)
     *
     * @param f Fuente
     * @param cuerpo Cuerpo
     * @return Fuente f
     */
    public static Fuente addCuerpo(Fuente f, Cuerpo cuerpo) {
        return f.addCuerpo(cuerpo);
    }

    /**
     * Obtiene los cuerpos y fundiones
     *
     * @return Cuerpos y fundiones
     */
    public List<Simbolo> getElementos() {
        return elementos;
    }

    /**
     * Establece los cuerpos y funciones
     *
     * @param elementos Lista de Cuerpos y funciones
     */
    public void setElementos(List<Simbolo> elementos) {
        this.elementos = elementos;
    }

    /**
     * Obtiene los cuerpos
     * @return Lista de cuerpos
     */
    public List<Cuerpo> getCuerpos() {
        return cuerpos;
    }

    /**
     * Obtiene las funciones
     * @return Lista de funciones
     */
    public List<FuncionDef> getFunciones() {
        return funciones;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return elementos.toArray(new Simbolo[elementos.size()]);
    }

}
