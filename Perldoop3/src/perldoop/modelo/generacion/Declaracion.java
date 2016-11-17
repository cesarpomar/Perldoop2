package perldoop.modelo.generacion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.semantica.Tipo;

/**
 * Clase para almacenar declaraciones en expresiones hasta ser escritas en el código fuente
 *
 * @author César Pomar
 */
public class Declaracion {

    private Simbolo simbolo;
    private Tipo tipo;
    private String nombre;
    private String valor;

    /**
     * Contruye una declaración con un valor inicial
     *
     * @param simbolo Simbolo generador de la declaracion
     * @param tipo Tipo de la variable a declarar
     * @param nombre Nombre de la variable a declarar
     * @param valor Valor inicial de la variable
     */
    public Declaracion(Simbolo simbolo, Tipo tipo, String nombre, String valor) {
        this.simbolo = simbolo;
        this.tipo = tipo;
        this.nombre = nombre;
        this.valor = valor;
    }

    /**
     * Contruye una declaracion sin inicializar
     *
     * @param simbolo Simbolo generador de la declaracion
     * @param tipo Tipo de la variable a declarar
     * @param nombre Nombre de la variable a declarar
     */
    public Declaracion(Simbolo simbolo, Tipo tipo, String nombre) {
        this.simbolo = simbolo;
        this.tipo = tipo;
        this.nombre = nombre;
    }

    /**
     * Contruye una declaracion para inicializar una variable ya declarada
     *
     * @param simbolo Simbolo generador de la declaracion
     * @param nombre Nombre de la variable a declarar
     * @param valor Valor inicial de la variable
     */
    public Declaracion(Simbolo simbolo, String nombre, String valor) {
        this.simbolo = simbolo;
        this.nombre = nombre;
        this.valor = valor;
    }

    /**
     * Obtiene el tipo
     *
     * @return Tipo
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo
     *
     * @param tipo Tipo
     */
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el nombre
     *
     * @return Nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre
     *
     * @param nombre Nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el valor
     *
     * @return Valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * Establece el valor
     *
     * @param valor Valor
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * Obtiene el simbolo generador
     *
     * @return Simbolo generador
     */
    public Simbolo getSimbolo() {
        return simbolo;
    }

    /**
     * Establece el simbolo generador
     *
     * @param simbolo Simbolo generador
     */
    public void setSimbolo(Simbolo simbolo) {
        this.simbolo = simbolo;
    }

}
