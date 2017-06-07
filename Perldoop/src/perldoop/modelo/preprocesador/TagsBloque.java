package perldoop.modelo.preprocesador;

import perldoop.modelo.lexico.Token;

/**
 * Etiques para modificar el comportamiento de un bloque
 *
 * @author César Pomar
 */
public class TagsBloque implements Tags {

    protected Token etiqueta;

    /**
     * Constructor por defecto
     *
     * @param etiqueta Etiqueta de bloque
     */
    public TagsBloque(Token etiqueta) {
        this.etiqueta = etiqueta;
    }

    /**
     * Obtiene la etiqueta
     *
     * @return Etiqueta
     */
    public final Token getEtiqueta() {
        return etiqueta;
    }

    /**
     * Establece la etiqueta
     *
     * @param etiqueta Etiqueta
     */
    public final void setEtiqueta(Token etiqueta) {
        this.etiqueta = etiqueta;
    }

    /**
     * Convierte la estancia a un hijo de la clase TagsBloque
     *
     * @param <T> Tipo del hijo
     * @param t Clase hijo
     * @return Instancia del hijo
     */
    public final <T extends TagsBloque> T to(Class<T> t) {
        return (T) this;
    }

    @Override
    public int getLinea() {
        return etiqueta.getLinea();
    }

}
