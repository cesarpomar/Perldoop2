package perldoop.modelo.arbol.lista;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa las reducciones -> <br>
 * lista :	expresion ',' lista<br>
         |	expresion ','<br>							
	 |	expresion<br>	
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
     * @param exp Expresión
     * @param coma Coma
     */
    public Lista(Expresion exp, Terminal coma) {
        this();
        add(exp, coma);
    }

    /**
     * Construye una lista con una expresión
     * @param exp Expresión
     */
    public Lista(Expresion exp) {
        this();
        add(exp);
    }

    /**
     * Invierte la lista para mejorar la eficiencia del insertado por la izquierda
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
     * @return Lista de elementos
     */
    public final List<Simbolo> getElementos() {
        invertida(false);
        return elementos;
    }

    /**
     * Obtiene solo las expresiones de la lista
     * @return Lista de expresiones
     */
    public final List<Expresion> getExpresiones() {
        invertida(false);
        return expresiones;
    }

    /**
     * Añade una expresión y una coma
     * @param exp Expresión
     * @param coma Coma
     * @return Esta instancia
     */
    public final Lista add(Expresion exp, Terminal coma) {
        invertida(true);
        elementos.add(coma);
        add(exp);
        coma.setPadre(this);
        return this;
    }

    /**
     * Añade una expresión
     * @param exp Expresión
     * @return Esta lista
     */
    public final Lista add(Expresion exp) {
        invertida(true);
        elementos.add(exp);
        expresiones.add(exp);
        exp.setPadre(this);
        return this;
    }

    /**
     * Realiza la misma acción que invocar l.add(exp,coma)
     * @param exp Expresion
     * @param coma Coma
     * @param l Lista
     * @return l
     */
    public static final Lista add(Expresion exp, Terminal coma, Lista l) {
        return l.add(exp, coma);
    }

    /**
     * Realiza la misma acción que invocar l.add(exp)
     * @param exp Expresion
     * @param l Lista
     * @return Lista l
     */
    public static final Lista add(Expresion exp, Lista l) {
        return l.add(exp);
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
