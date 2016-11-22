package perldoop.modelo.preprocesador;

import java.util.ArrayList;
import java.util.List;
import perldoop.modelo.lexico.Token;

/**
 * Etiquetas para inicializar colección
 *
 * @author César Pomar
 */
public final class TagsInicializacion implements Tags {

    private List<Token> sizes;

    /**
     * Constructor por defecto
     */
    public TagsInicializacion() {
        sizes = new ArrayList<>(5);
    }

    /**
     * Añade un tamaño
     *
     * @param size Tamaño
     */
    public void addSize(Token size) {
        sizes.add(size);
    }

    /* Obtiene todos los tamaños
     *
     * @return Tamaños
     */
    public List<Token> getSizes() {
        return sizes;
    }

    @Override
    public String toString() {
        return "EtiquetasInicializacion(" + sizes + ')';
    }
}
