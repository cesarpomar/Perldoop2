package perldoop.modelo.semantica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.preprocesador.EtiquetasTipo;

/**
 * Clase que almacena las bloques declaradas en el código.
 *
 * @author César Pomar
 */
public final class TablaSimbolos {

    private List<Map<String, Contexto>> bloques;
    private Map<String, EtiquetasTipo> predeclaraciones;
    private Map<String, EntradaFuncion> funciones;
    private Map<String, Token> funcionesNoDeclaradas;
    private Map<String, Paquete> paquetes;
    private Paquete paquete;
    private boolean vacia;

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
        funcionesNoDeclaradas = new HashMap<>(20);
        this.paquetes = paquetes;
        vacia = true;
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
    public void addVariable(EntradaVariable entrada, char contexto) {
        vacia = false;
        if (entrada.isPublica() && paquete != null) {
            paquete.addVariable(entrada, contexto);
        }
        entrada.setNivel(bloques.size() - 1);
        Contexto c = bloques.get(entrada.getNivel()).get(entrada.getIdentificador());
        if (c == null) {
            c = new Contexto();
            bloques.get(entrada.getNivel()).put(entrada.getIdentificador(), c);
        } else {
            entrada.setConflicto(true);
        }
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
     * Busca una variable en un nivel
     *
     * @param identificador Identificador
     * @param contexto Contexto
     * @param nivel Nivel
     * @return Entrada
     */
    public EntradaVariable buscarVariable(String identificador, char contexto, int nivel) {
        Contexto c = null;
        if (nivel > -1 && nivel < bloques.size()) {
            c = bloques.get(nivel).get(identificador);
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
     * Busca una variable en su contexto
     *
     * @param identificador Identificador
     * @param contexto Contexto
     * @return Entrada
     */
    public EntradaVariable buscarVariable(String identificador, char contexto) {
        Contexto c = buscarVariable(identificador);
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
     * Busca una variable
     *
     * @param identificador Identificador
     * @return Entrada
     */
    public Contexto buscarVariable(String identificador) {
        Contexto c;
        for (int i = bloques.size() - 1; i >= 0; i--) {
            c = bloques.get(i).get(identificador);
            if (c != null) {
                return c;
            }
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
    public void addDeclaracion(String identificador, EtiquetasTipo tipo) {
        predeclaraciones.put(identificador, tipo);
    }

    /**
     * Obtiene el tipo de una variable sin declarar y luego lo borra
     *
     * @param identificador Identificador
     * @return Tipo
     */
    public EtiquetasTipo getDeclaracion(String identificador) {
        return predeclaraciones.remove(identificador);
    }

    /**
     * Crea una funcion con su alias, si existe la sobreescribe
     *
     * @param entrada Entrada
     */
    public void addFuncion(EntradaFuncion entrada) {
        funcionesNoDeclaradas.remove(entrada.getIdentificador());
        funciones.put(entrada.getIdentificador(), entrada);
    }

    /**
     * Obtiene el alias de una función si existe
     *
     * @param identificador Identificador
     * @return Entrada función
     */
    public EntradaFuncion buscarFuncion(String identificador) {
        return funciones.get(identificador);
    }

    /**
     * Crea una entrada para una función que aun no ha sido declarada
     *
     * @param t Token
     * @return Alias de la función
     */
    public String addFuncionNoDeclarada(Token t) {
        funcionesNoDeclaradas.put(t.getValor(), t);
        return t.getValor();
    }

    /**
     * Comprueba si la tabla pertenece a un paquete
     *
     * @return Declaracion de paquete
     */
    public boolean isPaquete() {
        return paquete != null;
    }

    /**
     * Comprueba si la tabla de simbolos ha sido usada
     *
     * @return Tabla vacia
     */
    public boolean isVacia() {
        return vacia;
    }

    /**
     * Obtiene un paquete
     *
     * @param nombre Nombre del paquete
     * @return Paquete
     */
    public Paquete getPaquete(String nombre) {
        return paquetes.get(nombre);
    }

    /**
     * Crea un paquete con la tabla
     *
     * @param nombre Nombre del paquete
     */
    public void crerPaquete(String nombre) {
        if (paquete == null && isVacia()) {
            paquete = new Paquete(funciones);
            paquetes.put(nombre, paquete);
        }
    }

}
