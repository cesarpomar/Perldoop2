package perldoop.excepciones;

/**
 * Excepcion lanzada por el analizador semantico cuando encuentra un error.
 *
 * @author César Pomar
 */
public class ExcepcionSemantica extends RuntimeException {

    private String codigo;

    /**
     * Crea una excepción Semantica
     *
     * @param codigo Codigo de error que la lanzo
     */
    public ExcepcionSemantica(String codigo) {
        super("EXCEPCION SEMÁNTICA");
        this.codigo = codigo;
    }

    /**
     * Obtiene el código de error
     *
     * @return Código de error
     */
    public String getCodigo() {
        return codigo;
    }

}
