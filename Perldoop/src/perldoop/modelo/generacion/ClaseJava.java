package perldoop.modelo.generacion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Clase que representa una clase java
 *
 * @author César Pomar
 */
public final class ClaseJava {

    private List<String> paquetes;
    private Set<String> imports;
    private String nombre;
    private String clasePadre;
    private List<String> interfaces;
    private List<String> atributos;
    private List<StringBuilder> funciones;

    /**
     * Contructor único
     */
    public ClaseJava() {
        paquetes = new ArrayList<>(5);
        imports = new HashSet<>(10);
        interfaces = new ArrayList<>(5);
        atributos = new ArrayList<>(20);
        funciones = new ArrayList<>(20);
    }

    /**
     * Obtiene los paquetes
     *
     * @return Paquetes
     */
    public List<String> getPaquetes() {
        return paquetes;
    }

    /**
     * Establece los paquetes
     *
     * @param paquetes Paquetes
     */
    public void setPaquete(List<String> paquetes) {
        this.paquetes = paquetes;
    }

    /**
     * Obtiene los imports
     *
     * @return Imports
     */
    public Set<String> getImports() {
        return imports;
    }

    /**
     * Establece los imports
     *
     * @param imports Imports
     */
    public void setImports(Set<String> imports) {
        this.imports = imports;
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
     * Obtiene las interfaces a interfaces
     *
     * @return Interfaces a interfaces
     */
    public List<String> getInterfaces() {
        return interfaces;
    }

    /**
     * Establece las interfaces a interfaces
     *
     * @param interfaces Interfaces a interfaces
     */
    public void setInterfaces(List<String> interfaces) {
        this.interfaces = interfaces;
    }

    /**
     * Obtiene las clase a clasePadre
     *
     * @return Clase a clasePadre
     */
    public String getClasePadre() {
        return clasePadre;
    }

    /**
     * Establece la clase a clasePadre
     *
     * @param clasePadre Clase a clasePadre
     */
    public void setClasePadre(String clasePadre) {
        this.clasePadre = clasePadre;
    }

    /**
     * Obtiene los atributos
     *
     * @return Atributos
     */
    public List<String> getAtributos() {
        return atributos;
    }

    /**
     * Establece los atributos
     *
     * @param atributos Atributos
     */
    public void setAtributos(List<String> atributos) {
        this.atributos = atributos;
    }

    /**
     * Obtiene las funciones
     *
     * @return Funciones
     */
    public List<StringBuilder> getFunciones() {
        return funciones;
    }

    /**
     * Establece las funciones
     *
     * @param funciones Funciones
     */
    public void setFunciones(List<StringBuilder> funciones) {
        this.funciones = funciones;
    }

}
