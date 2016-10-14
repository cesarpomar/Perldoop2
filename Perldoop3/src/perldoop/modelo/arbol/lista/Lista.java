package perldoop.modelo.arbol.lista;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa las reducciones -&gt; <br>
 * lista :	expresion ',' lista<br>
 * |	expresion ','<br>
 * |	expresion<br>
 *
 * @author César Pomar
 */
public final class Lista extends Simbolo {

    private List<Simbolo> elementos;
    private List<Expresion> expresiones;
    private boolean invertida;

    /**
     * Constructor por defecto
     */
    public Lista() {
        invertida = true;
        elementos = new ArrayList<>(20);
        expresiones = new ArrayList<>(20);
    }

    /**
     * Construye una lista con una expresión y una coma
     *
     * @param exp Expresión
     * @param coma Coma
     */
    public Lista(Expresion exp, Terminal coma) {
        this();
        add(exp, coma);
    }

    /**
     * Construye una lista con una expresión
     *
     * @param exp Expresión
     */
    public Lista(Expresion exp) {
        this();
        add(exp);
    }

    /**
     * Invierte la lista para mejorar la eficiencia del insertado por la izquierda
     *
     * @param invertida listaInvertida
     */
    private void invertida(boolean invertida) {
        if (this.invertida != invertida) {
            this.invertida = invertida;
            Collections.reverse(elementos);
            Collections.reverse(expresiones);
        }
    }

    /**
     * Obtiene todos los elmentos de la lista
     *
     * @return Lista de elementos
     */
    public List<Simbolo> getElementos() {
        invertida(false);
        return elementos;
    }

    /**
     * Obtiene solo las expresiones de la lista
     *
     * @return Lista de expresiones
     */
    public List<Expresion> getExpresiones() {
        invertida(false);
        return expresiones;
    }

    /**
     * Añade una expresión y una coma
     *
     * @param exp Expresión
     * @param coma Coma
     * @return Esta instancia
     */
    public Lista add(Expresion exp, Terminal coma) {
        invertida(true);
        elementos.add(coma);
        add(exp);
        coma.setPadre(this);
        return this;
    }

    /**
     * Añade una expresión
     *
     * @param exp Expresión
     * @return Esta lista
     */
    public Lista add(Expresion exp) {
        invertida(true);
        elementos.add(exp);
        expresiones.add(exp);
        exp.setPadre(this);
        return this;
    }

    /**
     * Realiza la misma acción que invocar l.add(exp,coma)
     *
     * @param exp Expresion
     * @param coma Coma
     * @param l Lista
     * @return l
     */
    public static Lista add(Expresion exp, Terminal coma, Lista l) {
        return l.add(exp, coma);
    }

    /**
     * Realiza la misma acción que invocar l.add(exp)
     *
     * @param exp Expresion
     * @param l Lista
     * @return Lista l
     */
    public static Lista add(Expresion exp, Lista l) {
        return l.add(exp);
    }

    /**
     * Añade elementos a una lista eliminandolos de la cabeza de otra
     *
     * @param l Lista
     * @param n Numero de elementos
     */
    public void addLista(Lista l, int n) {
        this.invertida(false);
        l.invertida(true);
        List<Expresion> lexps = l.getExpresiones();
        List<Simbolo> lelems = l.getElementos();
        for (int i = 0; i < n; i++) {
            expresiones.add(lexps.remove(lexps.size() - 1));
            elementos.add(lelems.remove(lelems.size() - 1));
            if (!elementos.isEmpty()) {
                elementos.add(lelems.remove(lelems.size() - 1));
            }
        }
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return getElementos().toArray(new Simbolo[elementos.size()]);
    }

}
