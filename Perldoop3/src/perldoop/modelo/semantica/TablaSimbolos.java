package perldoop.modelo.semantica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.preprocesador.EtiquetasTipo;

/**
 * Paquete que almacena las bloques declaradas en el código.
 *
 * @author César Pomar
 */
public final class TablaSimbolos {

    private List<Map<String, ContextoVariable>> bloques;
    private Map<String, EtiquetasTipo> predeclaraciones;
    private Map<String, EntradaFuncion> funciones;
    private Map<String, EntradaFuncionNoDeclarada> funcionesNoDeclaradas;
    private ArbolPaquetes paquetes;
    private Map<String, Paquete> imports;
    private Paquete paquete;
    private boolean vacia;

    /**
     * Construye la tabla de símbolos
     *
     * @param paquetes Arbol de paquetes
     */
    public TablaSimbolos(ArbolPaquetes paquetes) {
        bloques = new ArrayList<>(20);
        bloques.add(new HashMap<>(20));//Atributos
        predeclaraciones = new HashMap<>(20);
        funciones = new HashMap<>(20);
        funcionesNoDeclaradas = new HashMap<>(20);
        this.paquetes = paquetes;
        imports = new HashMap<>(20);
        vacia = true;
    }

    /**
     * Abre un nuevo bloque de variables
     */
    public void abrirBloque() {
        bloques.add(new HashMap<>(20));
    }

    /**
     * Cierra el ultimo bloque de variable
     */
    public void cerrarBloque() {
        bloques.remove(bloques.size() - 1);
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
     * Añade una entrada al contexto de una variable
     *
     * @param entrada Entrada
     * @param contexto ContextoVariable
     */
    public void addVariable(EntradaVariable entrada, char contexto) {
        vacia = false;
        if (entrada.isPublica() && paquete != null) {
            paquete.addVariable(entrada, contexto);
        }
        entrada.setNivel(bloques.size() - 1);
        ContextoVariable c = bloques.get(entrada.getNivel()).get(entrada.getIdentificador());
        if (c == null) {
            c = new ContextoVariable();
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
     * @param contexto ContextoVariable
     * @param nivel Nivel
     * @return Entrada
     */
    public EntradaVariable buscarVariable(String identificador, char contexto, int nivel) {
        ContextoVariable c = null;
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
     * @param contexto ContextoVariable
     * @return Entrada
     */
    public EntradaVariable buscarVariable(String identificador, char contexto) {
        ContextoVariable c = buscarVariable(identificador);
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
    public ContextoVariable buscarVariable(String identificador) {
        ContextoVariable c;
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
     * Crea una funcion, si ya existe sobrescribe a la anterior
     *
     * @param entrada Entrada
     */
    public void addFuncion(EntradaFuncion entrada) {
        EntradaFuncionNoDeclarada noDeclarada = funcionesNoDeclaradas.remove(entrada.getIdentificador());
        funciones.put(entrada.getIdentificador(), entrada);
        if(noDeclarada!=null){
            entrada.setAlias(noDeclarada.getAlias());
        }
    }

    /**
     * Obtiene las funciones no declaradas
     *
     * @return Funciones no declaradas
     */
    public Map<String, EntradaFuncionNoDeclarada> getFuncionesNoDeclaradas() {
        return funcionesNoDeclaradas;
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

    /**
     * Obtiene una funcion no declarada
     *
     * @param identificador Identificador
     * @return Entrada función no declarada
     */
    public EntradaFuncionNoDeclarada buscarFuncionNoDeclarada(String identificador) {
        return funcionesNoDeclaradas.get(identificador);
    }

    /**
     * Crea una entrada para una función que aun no ha sido declarada
     *
     * @param t Token
     */
    public void addFuncionNoDeclarada(Token t) {
        EntradaFuncionNoDeclarada entrada = funcionesNoDeclaradas.get(t.getValor());
        if (entrada == null) {
            entrada = new EntradaFuncionNoDeclarada(t.getValor());
            funcionesNoDeclaradas.put(t.getValor(), entrada);
        }
        entrada.getLlamadas().add(t);
    }

    /**
     * Obtiene el paquete si esta declarado
     *
     * @return Paquete declarado
     */
    public Paquete getPaquete() {
        return paquete;
    }

    /**
     * Obtiene el arbol de paquetes
     *
     * @return Arbol de paquetes
     */
    public ArbolPaquetes getArbolPaquete() {
        return paquetes;
    }

    /**
     * Obtiene los paquetes importados
     *
     * @return Mapa de paquetes
     */
    public Map<String, Paquete> getImports() {
        return imports;
    }

    /**
     * Crea un paquete
     *
     * @param fichero Fichero
     */
    public void crearPaquete(String fichero) {
        String id=String.join(".", paquetes.getDirectorios(fichero))+"."+paquetes.getClases().get(fichero);
        paquete = new Paquete(paquetes.getClases().get(fichero), funciones);
        paquetes.getPaquetes().put(id, paquete);
    }

    /**
     * Obtiene un paquete
     *
     * @param fichero Fichero actual
     * @param paquete Paquete a importar
     * @return Paquete
     */
    public Paquete getPaquete(String fichero, String[] paquete) {
        String ruta = String.join(".",paquetes.getDirectorios(fichero))+"."+String.join(".",paquete);
        return paquetes.getPaquetes().get(ruta);
    }

}
