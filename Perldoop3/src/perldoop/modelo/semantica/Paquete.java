package perldoop.modelo.semantica;

import java.util.HashMap;
import java.util.Map;

/**
 * Paquete que representa las variables y funciones exportadas por una clase
 *
 * @author César Pomar
 */
public final class Paquete {

    private String fichero;
    private String identificador;
    private String alias;
    private Map<String, ContextoVariable> atributos;
    private Map<String, EntradaFuncion> funciones;

    /**
     * Constructor del paquete
     *
     * @param fichero Fichero del paquete
     * @param identifiador Identificador
     * @param funciones Funciones
     */
    public Paquete(String fichero, String identifiador, Map<String, EntradaFuncion> funciones) {
        this.identificador = identifiador;
        this.funciones = funciones;
        this.fichero = fichero;
        atributos = new HashMap<>(10);
    }

    /**
     * Obtiene la fichero del paquete
     *
     * @return Fichero del paquete
     */
    public String getFichero() {
        return fichero;
    }

    /**
     * Establece la fichero del paquete
     *
     * @param fichero Fichero del paquete
     */
    public void setFichero(String fichero) {
        this.fichero = fichero;
    }

    /**
     * Obtiene el identificador
     *
     * @return Identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece el identificador
     *
     * @param identificador Identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene el alias
     *
     * @return Alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Establece el alias
     *
     * @param alias Alias
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Obtiene los atributos
     *
     * @return Atributos
     */
    public Map<String, ContextoVariable> getAtributos() {
        return atributos;
    }

    /**
     * Establece los atributos
     *
     * @param atributos Atributos
     */
    public void setAtributos(Map<String, ContextoVariable> atributos) {
        this.atributos = atributos;
    }

    /**
     * Obtiene las funciones
     *
     * @return Funciones
     */
    public Map<String, EntradaFuncion> getFunciones() {
        return funciones;
    }

    /**
     * Establece las funciones
     *
     * @param funciones Funciones
     */
    public void setFunciones(Map<String, EntradaFuncion> funciones) {
        this.funciones = funciones;
    }

    /**
     * Añade una variable al paquete
     *
     * @param entrada Entrada
     */
    public void addVariable(EntradaVariable entrada) {
        ContextoVariable c = atributos.get(entrada.getIdentificador());
        if (c == null) {
            c = new ContextoVariable();
            atributos.put(entrada.getIdentificador(), c);
        }
        c.setVar(entrada);
    }

    /**
     * Busca una variable en su contexto
     *
     * @param identificador Identificador
     * @param contexto ContextoVariable
     * @return Entrada
     */
    public EntradaVariable buscarVariable(String identificador, char contexto) {
        ContextoVariable c = atributos.get(identificador);
        if (c == null) {
            return null;
        }
        return c.getVar(contexto);
    }

    /**
     * Obtiene una funcion
     *
     * @param identificador Identificador
     * @return Entrada función
     */
    public EntradaFuncion buscarFuncion(String identificador) {
        return funciones.get(identificador);
    }

}
