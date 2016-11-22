package perldoop.modelo.preprocesador.hadoop;

import perldoop.modelo.lexico.Token;

/**
 * Etiquetas para definir el tipo de un bloque hadoop mapper
 *
 * @author César Pomar
 */
public final class TagsHadoopMapper extends TagsHadoopApi {

    /**
     * Constructor por defecto
     *
     * @param tipo Tipo de API hadoop
     */
    public TagsHadoopMapper(Token tipo) {
        super(tipo);
    }

}
