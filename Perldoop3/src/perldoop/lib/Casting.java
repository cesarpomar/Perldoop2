package perldoop.lib;

/**
 * Intefaz para implementar los castings
 *
 * @author César Pomar
 */
public interface Casting {

    /**
     * Función donde se implementa el casting
     * @param <T> Tipo de la colección resultante
     * @return Colección resultante
     */
    <T> T casting();

}
