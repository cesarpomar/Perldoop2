package perldoop.modelo.arbol.lista;

import java.util.ArrayList;
import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa las reducciones -&gt; <br>
 * lista :	lista ',' expresion<br>
 * |	expresion<br>
 *
 * @author César Pomar
 */
public final class Lista extends Simbolo {

    private List<Simbolo> elementos;
    private List<Expresion> expresiones;
    private List<Terminal> separadores;

    /**
     * Construye una lista vacia
     */
    public Lista() {
        elementos = new ArrayList<>(20);
        expresiones = new ArrayList<>(20);
        separadores = new ArrayList<>(20);
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
     * Obtiene los elmentos de la lista
     *
     * @return Lista de elementos
     */
    public List<Simbolo> getElementos() {
        return elementos;
    }

    /**
     * Establece los elmentos de la lista
     * @param elementos Lista de elementos
     */
    public void setElementos(List<Simbolo> elementos) {
        this.elementos = elementos;
    }

    /**
     * Obtiene las expresiones de la lista
     *
     * @return Lista de expresiones
     */
    public List<Expresion> getExpresiones() {
        return expresiones;
    }

    /**
     * Establece las expresiones de la lista
     *
     * @param expresiones Lista de expresiones
     */
    public void setExpresiones(List<Expresion> expresiones) {
        this.expresiones = expresiones;
    }

    /**
     * Obtiene los separadores de las expresiones
     *
     * @return Lista de separadores
     */
    public List<Terminal> getSeparadores() {
        return separadores;
    }

    /**
     * Establece los separadores de las expresiones
     *
     * @param separadores Lista de separadores
     */
    public void setSeparadores(List<Terminal> separadores) {
        this.separadores = separadores;
    }

    /**
     * Añade una expresión y una coma
     *
     * @param coma Coma
     * @param exp Expresión
     * @return Esta instancia
     */
    public Lista add(Terminal coma, Expresion exp) {
        add(coma);
        add(exp);
        return this;
    }

    /**
     * Añade una expresion
     *
     * @param exp Expresión
     * @return Esta lista
     */
    public Lista add(Expresion exp) {
        expresiones.add(exp);
        elementos.add(exp);
        exp.setPadre(this);
        return this;
    }

    /**
     * Añade una coma
     *
     * @param coma Coma
     * @return Esta lista
     */
    public Lista add(Terminal coma) {
        separadores.add(coma);
        elementos.add(coma);
        coma.setPadre(this);
        return this;
    }

    /**
     * Realiza la misma acción que invocar l.add(coma,exp)
     *
     * @param l Lista
     * @param coma Coma
     * @param exp Expresion
     * @return l
     */
    public static Lista add(Lista l, Terminal coma, Expresion exp) {
        return l.add(coma, exp);
    }

    /**
     * Realiza la misma acción que invocar l.add(coma)
     *
     * @param l Lista
     * @param coma Coma
     * @return Lista l
     */
    public static Lista add(Lista l, Terminal coma) {
        return l.add(coma);
    }

    /**
     * Añade el ultimo elemento de otra lista
     *
     * @param l Lista
     * @return Esta lista
     */
    public Lista addLast(Lista l) {
        Expresion exp;
        add(exp = l.expresiones.remove(l.expresiones.size() - 1));
        if (exp == l.elementos.get(l.elementos.size() - 1)) {
            add((Expresion) l.elementos.get(l.elementos.size() - 1));
        } else {
            add((Terminal) l.elementos.get(l.elementos.size() - 1));
            add((Expresion) l.elementos.get(l.elementos.size() - 1));
        }
        return this;
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
