package perldoop.sintactico;

import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.expresion.Expresion;
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
    public ParseValLista(Simbolo lista, Expresion exp, Lista[] args, List<Simbolo> simbolos) {
        super(lista);
        ((Lista) lista).add(exp);
        this.args = args[0];
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
    public static ParseValLista add(ParserVal pv, Terminal coma, Expresion exp, Lista[] args) {
        ParseValLista lista = (ParseValLista) pv;
        if (lista.args == null) {
            ((Lista) lista.get()).add(coma, exp);
        } else {
            lista.args.add(coma, exp);
            lista.dependencias();
        }
        if (args != null) {
            lista.args = args[0];
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
     * Comprueba cual de las listas debe ser el nuevo capturador de arugmentos de funcion
     *
     * @param l Lista nueva
     * @param args Lista actual
     * @return Lista nueva
     */
    public static Lista args(Lista l, Lista[] args) {
        if (args[0] == null) {
            args[0] = l;
        }
        return l;
    }

}
