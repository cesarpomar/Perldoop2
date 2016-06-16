package perldoop.semantico.excepciones;

/**
 * Excepcion lanzada por el analizador semantico cuando encuentra un error.
 *
 * @author César Pomar
 */
public class ExcepcionSemantica extends RuntimeException {

    public ExcepcionSemantica() {
        super("EXCEPCION SEMÁNTICA");
    }

}
