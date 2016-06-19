package perldoop.modelo.generacion;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un bloque de codigo en java
 * @author César Pomar
 */
public class BloqueJava implements CodigoJava{
    
    private String cabecera;
    private List<CodigoJava> codigo;
    private String pie;

    /**
     * Constructor único
     */
    public BloqueJava() {
        codigo = new ArrayList<>(100);
    }

    /**
     * Obtiene la cabecera
     * @return Cabecera
     */
    public String getCabecera() {
        return cabecera;
    }

    /**
     * Establece la cabecera
     * @param cabecera Cabecera
     */
    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    /**
     * Obtiene el código de la clase
     * @return Código de la clase
     */
    public List<CodigoJava> getCodigo() {
        return codigo;
    }

    /**
     * Establece el código de la clase
     * @param codigo Código de la clase
     */
    public void setCodigo(List<CodigoJava> codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el pie
     * @return Pie
     */
    public String getPie() {
        return pie;
    }

    /**
     * Establece el pie
     * @param pie Pie
     */
    public void setPie(String pie) {
        this.pie = pie;
    }
    
    
    
}
