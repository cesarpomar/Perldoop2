package perldoop.sintactico;

import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.Funcion;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.sintactico.ParserVal;

/**
 * Reducion del analizador sintactico que contiene una lista
 *
 * @author César Pomar
 */
public final class ParseValLista extends ParserVal {

    private Lista args;
    private List<Simbolo> simbolos;

    /**
     * Constructor por defecto
     *
     * @param lista Lista
     * @param exp Expresion
     * @param args Argumento
     * @param simbolos Lista de simbolos del analizador
     */
    public ParseValLista(Simbolo lista, Expresion exp, Lista args, List<Simbolo> simbolos) {
        super(lista);
        ((Lista) lista).add(exp);
        if (check(exp)) {
            this.args = args;
        }
        this.simbolos = simbolos;
    }

    /**
     * Añade un elemento a la lista
     *
     * @param pv ParserVal
     * @param coma Coma
     * @param exp Expresion
     * @param args Lista argumento
     * @return Lista
     */
    public static ParseValLista add(ParserVal pv, Terminal coma, Expresion exp, Lista args) {
        ParseValLista lista = (ParseValLista) pv;
        if (lista.args == null) {
            ((Lista) lista.get()).add(coma, exp);
        } else {
            lista.args.add(coma, exp);
            lista.dependencias();
        }
        if (args != null && check(exp)) {
            lista.args = args;
        }
        return lista;
    }

    /**
     * Añade un elemento a la lista
     *
     * @param pv ParserVal
     * @param coma Coma
     * @return Lista
     */
    public static ParseValLista add(ParserVal pv, Terminal coma) {
        ParseValLista lista = (ParseValLista) pv;
        if (lista.args == null) {
            ((Lista) lista.get()).add(coma);
        } else {
            lista.args.add(coma);
            lista.dependencias();
        }
        return lista;
    }

    /**
     * Ajusta la lista de simbolos con las dependencias de padres
     */
    private void dependencias() {
        int p = 0;
        for (int i = simbolos.size() - 1; i > 0; i--) {
            if (simbolos.get(i) == args) {
                p = i;
                break;
            }
        }
        simbolos.add(simbolos.remove(p));
        Simbolo padre = args.getPadre();
        while (padre != this.get()) {
            for (int i = p; i < simbolos.size(); i++) {
                if (simbolos.get(i) == padre) {
                    p = i;
                    simbolos.add(simbolos.remove(p));
                    break;
                }
            }
            padre = padre.getPadre();
        }
    }

    /**
     * Para que la funcion absorba los paramentros debe tener la maxima precedencia de la expresion
     *
     * @param exp Expresiom
     * @return Funcion acepta argumentos
     */
    private static boolean check(Expresion exp) {
        Simbolo[] hijos = new Simbolo[]{exp};
        Simbolo find;
        do {
            if (hijos.length == 0) {
                return false;
            }
            find = hijos[hijos.length - 1];
            hijos = find.getHijos();
        } while (!(find instanceof Funcion));
        return true;
    }

}
