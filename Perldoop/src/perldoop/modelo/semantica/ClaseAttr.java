package perldoop.modelo.semantica;

/**
 * Clase para almacenar los traibutos de la clase a generar
 *
 * @author César Pomar
 */
public final class ClaseAttr {

    private boolean main; //La clase tiene un main
    private String padre;//En caso de herencia para identificar el padre

    /**
     * Comprueba si la clase tiene main
     *
     * @return Clase tiene main
     */
    public boolean isMain() {
        return main;
    }

    /**
     * Establece si la clase tiene main
     *
     * @param main Clase tiene main
     */
    public void setMain(boolean main) {
        this.main = main;
    }

    /**
     * Obtiene el padre de la clase
     *
     * @return Padre de la clase
     */
    public String getPadre() {
        return padre;
    }

    /**
     * Establece el padre de la clase
     *
     * @param padre Padre de la clase
     */
    public void setPadre(String padre) {
        this.padre = padre;
    }

}
