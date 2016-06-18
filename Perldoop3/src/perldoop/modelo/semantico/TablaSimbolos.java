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
public final class TablaSimbolos {

    private List<Map<String, Contexto>> bloques;
    private Map<String, Tipo> predeclaraciones;
    private Map<String, TipoFuncion> funciones;
    private Map<String, Paquete> paquetes;
    private Paquete paquete;

    /**
     * Construye la tabla de símbolos
     *
     * @param paquetes Paquetes
     */
    public TablaSimbolos(Map<String, Paquete> paquetes) {
        bloques = new ArrayList<>(20);
        bloques.add(new HashMap<>(10));//Atributos
        predeclaraciones = new HashMap<>(20);
        funciones = new HashMap<>(20);
        this.paquetes = paquetes;
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
     * Obtiene el número de bloques
     *
     * @return Número de Bloques
     */
    public int getBloques() {
        return bloques.size();
    }

    /**
     * Añade un tipo a una variable sin declarar
     *
     * @param identificador Identificador
     * @param tipo Tipo
     */
    public void addDeclaracion(String identificador, Tipo tipo) {
        predeclaraciones.put(identificador, tipo);
    }

    /**
     * Obtiene el tipo de una variable sin declarar
     *
     * @param identificador Identificador
     * @return Tipo
     */
    public Tipo buscarDeclaracion(String identificador) {
        return predeclaraciones.get(identificador);
    }

    /**
     * Obtiene si existe un paquete
     *
     * @return Declaracion de paquete
     */
    public boolean isPaquete() {
        return paquete != null;
    }

    /**
     * Crea un paquete con la tabla
     * @param nombre Nombre del paquete
     */
    public void crerPaquete(String nombre) {
        if (paquete == null) {
            paquete = new Paquete();
            paquetes.put(nombre, paquete);
        }
    }

}
