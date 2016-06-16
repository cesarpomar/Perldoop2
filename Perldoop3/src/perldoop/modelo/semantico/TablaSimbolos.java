package perldoop.modelo.semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que almacena las bloques declaradas en el código.
 *
 * @author César Pomar
 */
public class TablaSimbolos {

    private List<Map<String, Contexto>> bloques;

    /**
     * Construye la tabla de símbolos
     */
    public TablaSimbolos() {
        bloques = new ArrayList<>(20);
        bloques.add(new HashMap<>(10));
    }

    /**
     * Abre un nuevo bloque de bloques
     */
    public void abrirBloque() {
        bloques.add(new HashMap<>());
    }

    /**
     * Cierra el ultimo bloque bloques
     */
    public void cerrarBloque() {
        bloques.remove(bloques.size() - 1);
    }

    /**
     * Añade una variable
     *
     * @param entrada Entrada
     * @param contexto Contexto
     */
    public void addVariable(EntradaTabla entrada, char contexto) {
        entrada.setNivel(bloques.size() - 1);
        Contexto c = bloques.get(entrada.getNivel()).get(entrada.getIdentificador());
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
     * Busca una variable
     *
     * @param identificador Identificador
     * @param contexto Contexto
     * @return Entrada
     */
    public EntradaTabla buscarVariable(String identificador, char contexto) {
        Contexto c = null;
        for (int i = bloques.size() - 1; i >= 0; i--) {
            c = bloques.get(i).get(identificador);
            if (c != null) {
                break;
            }
        }
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
     * obtiene el número de niveles
     *
     * @return Número del niveles
     */
    public int getNiveles() {
        return bloques.size();
    }

}
