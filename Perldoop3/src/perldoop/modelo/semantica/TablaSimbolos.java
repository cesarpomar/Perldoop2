package perldoop.modelo.semantica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import perldoop.modelo.Opciones;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.preprocesador.TagsTipo;

/**
 * Paquete que almacena las bloques declaradas en el código.
 *
 * @author César Pomar
 */
public final class TablaSimbolos {

    private List<Map<String, ContextoVariable>> bloques;
    private Map<String, TagsTipo> predeclaraciones;
    private Map<String, EntradaFuncion> funciones;
    private Map<String, EntradaFuncionNoDeclarada> funcionesNoDeclaradas;
    private ArbolPaquetes paquetes;
    private Opciones opciones;
    private Jimporter jImporter;
    private Map<String, Paquete> imports;
    private Paquete paquete;
    private boolean vacia;

    /**
     * Construye la tabla de símbolos
     *
     * @param paquetes Arbol de paquetes
     * @param opciones Opciones
     */
    public TablaSimbolos(ArbolPaquetes paquetes, Opciones opciones) {
        bloques = new ArrayList<>(20);
        bloques.add(new HashMap<>(20));//Atributos
        predeclaraciones = new HashMap<>(20);
        funciones = new HashMap<>(20);
        funcionesNoDeclaradas = new HashMap<>(20);
        this.paquetes = paquetes;
        this.opciones = opciones;
        jImporter = (opciones.isjImporter()) ? new Jimporter(paquetes) : null;
        imports = new HashMap<>(20);
        inicializacion();
    }

    /**
     * Inicializa la tabla de simbolos
     */
    private void inicializacion() {
        addVariable(new EntradaVariable("ARGV", new Tipo(Tipo.ARRAY, Tipo.STRING), "Pd.ARGV", false));
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
     * Añade una variable a la tabla de simbolos y retorna la anterior en el caso de que substituya una existente
     *
     * @param entrada Entrada
     * @return Variable subtituida
     */
    public EntradaVariable addVariable(EntradaVariable entrada) {
        vacia = false;
        entrada.setNivel(bloques.size() - 1);
        if (entrada.isPublica() && paquete != null) {
            paquete.addVariable(entrada);
        }
        ContextoVariable c = bloques.get(entrada.getNivel()).get(entrada.getIdentificador());
        if (c == null) {
            c = new ContextoVariable();
            entrada.setConflicto(!buscarVariable(entrada.getIdentificador()).isEmpty());
            bloques.get(entrada.getNivel()).put(entrada.getIdentificador(), c);
        } else {
            entrada.setConflicto(true);
        }
        return c.setVar(entrada);
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
        ContextoVariable c = bloques.get(nivel).get(identificador);
        if (c == null) {
            return null;
        }
        return c.getVar(contexto);
    }

    /**
     * Busca una variable en su contexto
     *
     * @param identificador Identificador
     * @param contexto ContextoVariable
     * @return Entrada
     */
    public EntradaVariable buscarVariable(String identificador, char contexto) {
        ContextoVariable c;
        EntradaVariable variable;
        for (int i = bloques.size() - 1; i >= 0; i--) {
            c = bloques.get(i).get(identificador);
            if (c != null && (variable = c.getVar(contexto)) != null) {
                return variable;
            }
        }
        return null;
    }

    /**
     * Busca los contextos de una variable
     *
     * @param identificador Identificador
     * @return Lista de entradas que contienen la variable
     */
    public List<ContextoVariable> buscarVariable(String identificador) {
        List<ContextoVariable> cs = new ArrayList<>(bloques.size());
        for (int i = bloques.size() - 1; i >= 0; i--) {
            ContextoVariable c = bloques.get(i).get(identificador);
            if (c != null) {
                cs.add(c);
            }
        }
        return cs;
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
    public void addDeclaracion(String identificador, TagsTipo tipo) {
        predeclaraciones.put(identificador, tipo);
    }

    /**
     * Obtiene el tipo de una variable sin declarar y luego lo borra
     *
     * @param identificador Identificador
     * @return Tipo
     */
    public TagsTipo getDeclaracion(String identificador) {
        return predeclaraciones.remove(identificador);
    }

    /**
     * Crea una funcion, si ya existe sobrescribe a la anterior
     *
     * @param entrada Entrada
     */
    public void addFuncion(EntradaFuncion entrada) {  
        entrada.setConflicto(buscarFuncion(entrada.getIdentificador()) != null);
        funciones.put(entrada.getIdentificador(), entrada);
        EntradaFuncionNoDeclarada noDeclarada = funcionesNoDeclaradas.remove(entrada.getIdentificador());
        if (noDeclarada != null) {
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
        String ruta = String.join(".", paquetes.getDirectorios(fichero)) + "." + paquetes.getClases().get(fichero);
        paquete = new Paquete(ruta, paquetes.getClases().get(fichero), funciones);
        paquetes.getPaquetes().put(ruta, paquete);
    }

    /**
     * Obtiene un paquete
     *
     * @param fichero Fichero actual
     * @param paquete Paquete a importar
     * @return Paquete
     */
    public Paquete getPaquete(String fichero, String[] paquete) {
        //Busqueda relativa, libreria de usuario
        String ruta = String.join(".", paquetes.getDirectorios(fichero)) + "." + String.join(".", paquete);
        Paquete p = paquetes.getPaquetes().get(ruta);
        if (p != null) {
            return p;
        }
        //Busqueda absoluta, libreria de perl
        ruta = String.join(".", paquete);
        p = paquetes.getPaquetes().get(ruta);
        if (p == null && jImporter != null) {
            return jImporter.importar(ruta);
        }
        return p;
    }

}
