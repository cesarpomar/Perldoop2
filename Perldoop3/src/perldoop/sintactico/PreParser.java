package perldoop.sintactico;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.sintactico.ParserVal;

/**
 * Preanaliza los terminales para hacer una ampliacion de tipos y poder ampliar la gramatica sin conflictos
 *
 * @author César Pomar
 */
public final class PreParser {

    private List<Terminal> lista;
    private int i;
    private final static Set<Integer> FILE_HANDLE_CHAR = fileHandleChar();

    /**
     * Construye el productor con una lista de terminales
     *
     * @param lista Lista
     */
    public PreParser(List<Terminal> lista) {
        this.lista = lista;
    }

    /**
     * Obtiene el siguiente terminal
     *
     * @param pv ParseVal donde se almacenará el terminal
     * @return Tipo del terminal
     */
    public int next(ParserVal pv) {
        Terminal t = lista.get(i++);
        pv.set(t);
        switch (t.getToken().getTipo()) {
            case Parser.ID:
                return tipoId(i - 1);
            case '$':
                return tipoHandleFile(i - 1);
            case Parser.STDOUT:
                return tipoHandle(i - 1, Parser.STDOUT_H);
            case Parser.STDERR:
                return tipoHandle(i - 1, Parser.STDERR_H);
            default:
                return t.getToken().getTipo();
        }
    }

    /**
     * Obtiene el tipo de un terminal con token Id
     *
     * @param i Posicion del terminal
     * @return Tipo del id
     */
    private int tipoId(int i) {
        if (lista.size() > i + 1 && lista.get(i + 1).getToken().getTipo() == '(') {
            return Parser.ID_P;
        }
        return Parser.ID;

    }

    /**
     * Obtiene el tipo de un terminal con token handle
     *
     * @param i Posicion del terminal
     * @param tipo Tipo de handle
     * @return Tipo del handle
     */
    private int tipoHandle(int i, int tipo) {
        if (lista.size() > i + 1 && FILE_HANDLE_CHAR.contains(lista.get(i + 2).getToken().getTipo()) && isFuncion(i)) {
            return tipo;
        }
        return lista.get(i).getToken().getTipo();
    }

    /**
     * Obtiene el tipo de un terminal con token handle
     *
     * @param i Posicion del terminal
     * @param tipo Tipo de handle
     * @return Tipo del handle
     */
    private int tipoHandleFile(int i) {
        if (lista.size() > i + 2 
                && lista.get(i + 1).getToken().getTipo() == Parser.VAR
                && FILE_HANDLE_CHAR.contains(lista.get(i + 2).getToken().getTipo())
                && isFuncion(i)) {
            return Parser.FILE;
        }
        return '$';
    }

    /**
     * Comprueba si estamos en una funcion
     *
     * @param i Posicion del terminal
     * @return Es función
     */
    public boolean isFuncion(int i) {
        return i > 0 && (lista.get(i - 1).getToken().getTipo() == Parser.ID
                || (lista.get(i - 1).getToken().getTipo() == '(' && i > 1 && lista.get(i - 2).getToken().getTipo() == Parser.ID));
    }

    /**
     * Almacena los caracteres los cuales identifican un file handle frente a una variable corriente
     *
     * @return Mapa de carateres identificadores
     */
    public static Set<Integer> fileHandleChar() {
        return new HashSet<>((List) Arrays.asList(
                (int) '$',
                (int) '@',
                (int) '\'',
                (int) '"',
                (int) '(',
                Parser.ENTERO,
                Parser.DECIMAL,
                Parser.Q, Parser.QQ, Parser.QR, Parser.QW, Parser.QX
        ));
    }

}
