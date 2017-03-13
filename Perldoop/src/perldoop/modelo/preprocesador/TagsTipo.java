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
    private TagsInicializacion inicializacion;

    /**
     * Constructor por defecto
     */
    public TagsTipo() {
        tipos = new ArrayList<>(5);
    }

    /**
     * Constructor con inicializacion
     *
     * @param inicializacion Tags Inicializacion
     */
    public TagsTipo(TagsInicializacion inicializacion) {
        tipos = new ArrayList<>(5);
        this.inicializacion = inicializacion;
    }

    /**
     * Añade un tipo
     *
     * @param tipo Tipo
     */
    public void addTipo(Token tipo) {
        tipos.add(tipo);
        if (inicializacion != null) {
            inicializacion.addSize(null);
        }
    }

    /**
     * Establece un tamaño al ultimo tipo añadido
     *
     * @param size Tamaño
     */
    public void setSize(Token size) {
        List<Token> sizes = inicializacion.getSizes();
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

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(100);
        str.append("EtiquetasTipo(");
        for (int i = 0; i < tipos.size(); i++) {
            str.append(tipos.get(i));
        }
        str.append(")");
        return str.toString();
    }

    @Override
    public int getLinea() {
        return tipos.get(0).getLinea();
    }

}
