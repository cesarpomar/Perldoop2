package perldoop.modelo.preprocesador.hadoop;

import perldoop.modelo.lexico.Token;
import perldoop.modelo.preprocesador.TagsComportamiento;

/**
 * Etiquetas para definir el comportamiento de un bloque transformando a hadoop
 *
 * @author César Pomar
 */
public final class TagsHadoop extends TagsComportamiento {

    private TagsHadoopApi hadoopApi;

    /**
     * Constructor por defecto
     *
     * @param etiqueta Etiqueta de comportamiento
     */
    public TagsHadoop(Token etiqueta) {
        super(etiqueta);
    }

    /**
     * Obtiene las etiquetas de tipo hadoop
     *
     * @return Etiquetas tipo hadoop
     */
    public TagsHadoopApi getHadoopApi() {
        return hadoopApi;
    }

    /**
     * Establece las etiquetas tipo hadoop
     *
     * @param hadoopApi Etiquetas tipo hadoop
     */
    public void setHadoopApi(TagsHadoopApi hadoopApi) {
        this.hadoopApi = hadoopApi;
    }

}
