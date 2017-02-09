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

    private Token smart;
    private int linea;
    private List<Token> sizes;

    /**
     * Constructor por defecto
     *
     * @param linea Linea de la inicializacion
     */
    public TagsInicializacion(int linea) {
        sizes = new ArrayList<>(5);
        this.linea = linea;
    }

    /**
     * Constructor de inicializacion smart
     *
     * @param smart Token smart
     */
    public TagsInicializacion(Token smart) {
        sizes = new ArrayList<>(5);
        this.smart = smart;
        this.linea = smart.getLinea();
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

    /**
     * Obtiene el token smart
     *
     * @return Token smart
     */
    public Token getSmart() {
        return smart;
    }

    @Override
    public String toString() {
        if (smart == null) {
            return "EtiquetasInicializacion(" + sizes + ')';
        }
        return "EtiquetasInicializacion(" + smart + ", " + sizes + ')';
    }

    @Override
    public int getLinea() {
        return linea;
    }
}
