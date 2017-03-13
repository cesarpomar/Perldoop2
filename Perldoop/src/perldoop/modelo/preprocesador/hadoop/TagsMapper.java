package perldoop.modelo.preprocesador.hadoop;

import perldoop.modelo.lexico.Token;

/**
 * Etiquetas para definir el tipo de un bloque como mapper
 *
 * @author César Pomar
 */
public final class TagsMapper extends TagsHadoopApi {

    /**
     * Constructor por defecto
     *
     * @param etiqueta Etiqueta de bloque
     */
    public TagsMapper(Token etiqueta) {
        super(etiqueta);
    }

}
