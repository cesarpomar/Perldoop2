package perldoop.modelo.sintactico;

import perldoop.modelo.arbol.Simbolo;

/**
 * Valor de reduccion de reglas en el analizador sintactico
 *
 * @author César Pomar
 */
public class ParserVal {

    private Simbolo s;

    /**
     * Contruye un valor vacio
     */
    public ParserVal() {
    }

    /**
     * Construye un valor con símbolo
     *
     * @param s Simbolo
     */
    public ParserVal(Simbolo s) {
        this.s = s;
    }

    /**
     * Obtiene el símbolo
     *
     * @return Símbolo
     */
    public Simbolo get() {
        return s;
    }

    /**
     * Establece el símbolo
     *
     * @param s Símbolo
     */
    public void set(Simbolo s) {
        this.s = s;
    }

}
