package perldoop.modelo.preprocesador;

import java.util.ArrayList;
import java.util.List;
import perldoop.modelo.lexico.Token;

/**
 * Etiquetas de declaracion de tipo
 *
 * @author César Pomar
 */
public final class TagsTipo implements Tags {

    private List<Token> tipos;
    private List<Token> sizes;

    /**
     * Constructor por defecto
     */
    public TagsTipo() {
        tipos = new ArrayList<>(5);
        sizes = new ArrayList<>(5);
    }

    /**
     * Añade un tipo
     *
     * @param tipo Tipo
     */
    public void addTipo(Token tipo) {
        tipos.add(tipo);
        sizes.add(null);
    }

    /**
     * Establece un tamaño al ultimo tipo añadido
     *
     * @param size Tamaño
     */
    public void setSize(Token size) {
        sizes.set(sizes.size() - 1, size);
    }

    /**
     * Obtiene todos los tipos
     *
     * @return Tipos
     */
    public List<Token> getTipos() {
        return tipos;
    }

    /**
     * Obtiene todos los tamaños
     *
     * @return Tamaños
     */
    public List<Token> getSizes() {
        return sizes;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(100);
        str.append("EtiquetasTipo(");
        for (int i = 0; i < tipos.size(); i++) {
            str.append(tipos.get(i));
            if (sizes.get(i) != null) {
                str.append(sizes.get(i));
            }
        }
        str.append(")");
        return str.toString();
    }

}
