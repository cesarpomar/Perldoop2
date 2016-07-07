package perldoop.modelo.semantica;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa un paquete
 * @author César Pomar
 */
public final class Paquete {

    private Map<String, Contexto> atributos;

    /**
     * Constructor del paquete
     */
    public Paquete() {
        atributos = new HashMap<>(10);
    }

    /**
     * Añade una variable al paquete
     *
     * @param entrada Entrada
     * @param contexto Contexto
     */
    public void addVariable(EntradaVariable entrada, char contexto) {
        Contexto c = atributos.get(entrada.getIdentificador());
        switch (contexto) {
            case '$':
                c.setEscalar(entrada);
                break;
            case '@':
                c.setArray(entrada);
                break;
            case '%':
                c.setHash(entrada);
                break;
        }
    }

    /**
     * Busca una variable en su contexto dentro del paquete
     *
     * @param identificador Identificador
     * @param contexto Contexto
     * @return Entrada
     */
    public EntradaVariable buscarVariable(String identificador, char contexto) {
        Contexto c = atributos.get(identificador);
        if (c == null) {
            return null;
        }
        switch (contexto) {
            case '$':
                return c.getEscalar();
            case '@':
                return c.getArray();
            case '%':
                return c.getHash();
        }
        return null;
    }

    /**
     * Busca una variable dentro del paquete
     *
     * @param identificador Identificador
     * @return Entrada
     */
    public Contexto buscarVariable(String identificador) {
        return atributos.get(identificador);
    }

}
