package perldoop.sintactico;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.funcion.Funcion;
import perldoop.modelo.arbol.lista.Lista;

/**
 * Estructura la funciones sin parentesis
 *
 * @author César Pomar
 */
public final class FunctionParser {

    private List<Funcion> funciones;
    private List<Simbolo> simbolos;

    /**
     * Constructor por defecto
     *
     * @param simbolos Simbolos
     */
    public FunctionParser(List<Simbolo> simbolos) {
        funciones = new ArrayList<>(20);
        this.simbolos = simbolos;
    }

    /**
     * Añade una funcion
     *
     * @param f Funcion
     * @return Funcion añadida
     */
    public Funcion add(Funcion f) {
        funciones.add(f);
        return f;
    }

    /**
     * Busca la posicion del simbolo
     *
     * @param s Simbolo
     * @return Posicion
     */
    private int indexOf(Simbolo s) {
        for (int i = simbolos.size() - 1; i > -1; i--) {
            if (simbolos.get(i).equals(s)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Estructura todas las funciones añadidas
     */
    public void parse() {
        ListIterator<Funcion> it = funciones.listIterator(funciones.size());
        while (it.hasPrevious()) {
            Funcion f = it.previous();
            Simbolo padre = f.getPadre();
            Simbolo s = padre.getPadre();
            if (s instanceof Lista) {
                Lista l = ((Lista) s);
                adaptar(f, l, l.getExpresiones().indexOf(padre));
            }
            it.remove();
        }
    }

    /**
     * Adapta la funcion
     *
     * @param f Funcion
     * @param l Lista con argumentos
     * @param pos Posicion de la funcion en la lista
     */
    public void adaptar(Funcion f, Lista l, int pos) {
        //Si no hay argumentos
        if (l.getExpresiones().size() <= pos + 1) {
            return;
        }
        Lista args = f.getColeccion().getLista();
        //Movemos los argumentos
        mover(args, l.getExpresiones(), pos + 1, args.getExpresiones());
        mover(args, l.getElementos(), pos * 2 + 1, args.getElementos());
        mover(args, l.getSeparadores(), pos, args.getSeparadores());
        //Ajustamos la lista
        int inicio = indexOf(args);
        int fin = indexOf(args.getElementos().get(args.getElementos().size() - 1));
        for (int i = 0; i < 5; i++) {//Lista, Coleccion, Funcion, StcFuncion, Lista
            for (int j = inicio; j < fin; j++) {
                simbolos.set(j, simbolos.set(j + 1, simbolos.get(j)));
            }
        }
    }

    /**
     * Mueve todos los elementos de la lista origen a la destino desde el elemento inicio
     *
     * @param padre Padre de los simbolos
     * @param org Lista origen
     * @param inicio Posicion inicio
     * @param dest Lista destino
     */
    public void mover(Simbolo padre, List org, int inicio, List dest) {
        if (org.size() > inicio) {
            List<Simbolo> sub = org.subList(inicio, org.size());
            dest.addAll(sub);
            sub.stream().forEach((Simbolo s) -> s.setPadre(padre));
            sub.clear();
        }
    }

}
