package perldoop.modelo.generacion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Clase que representa una clase java
 * @author César Pomar
 */
public final class ClaseJava {

    private List<String> paquetes;
    private Set<String> imports;
    private String nombre;
    private List<String> implementar;
    private String heredar;
    private BloqueJava codigo;

    /**
     * Contructor único
     */
    public ClaseJava() {
        paquetes = new ArrayList<>(5);
        imports = new HashSet<>(10);
        implementar = new ArrayList<>(5);
        codigo = new BloqueJava();
    }

    /**
     * Obtiene los paquetes
     * @return Paquetes
     */
    public List<String> getPaquetes() {
        return paquetes;
    }

    /**
     * Establece los paquetes
     * @param paquetes Paquetes
     */
    public void setPaquetes(List<String> paquetes) {
        this.paquetes = paquetes;
    }
    
    /**
     * Obtiene los imports
     * @return Imports
     */
    public Set<String> getImports() {
        return imports;
    }

    /**
     * Establece los imports
     * @param imports Imports
     */
    public void setImports(Set<String> imports) {
        this.imports = imports;
    }

    /**
     * Obtiene el nombre
     * @return Nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre
     * @param nombre Nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene las interfaces a implementar
     * @return Interfaces a implementar
     */
    public List<String> getImplementar() {
        return implementar;
    }

    /**
     * Establece las interfaces a implementar
     * @param implementar Interfaces a implementar
     */
    public void setImplementar(List<String> implementar) {
        this.implementar = implementar;
    }

    /**
     * Obtiene las clase a heredar
     * @return Clase a heredar
     */
    public String getHeredar() {
        return heredar;
    }

    /**
     * Establece la clase a heredar
     * @param heredar Clase a heredar
     */
    public void setHeredar(String heredar) {
        this.heredar = heredar;
    }

    /**
     * Obtiene el código de la clase
     *
     * @return Código de la clase
     */
    public BloqueJava getCodigo() {
        return codigo;
    }

    /**
     * Establece el código de la clase
     *
     * @param codigo Código de la clase
     */
    public void setCodigo(BloqueJava codigo) {
        this.codigo = codigo;
    }



}
