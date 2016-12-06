package perldoop.generacion.bloque.especial;

import java.util.List;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.preprocesador.TagsBloque;
import perldoop.modelo.preprocesador.hadoop.TagsHadoopApi;
import perldoop.modelo.preprocesador.hadoop.TagsMapper;
import perldoop.util.Buscar;

/**
 * Clase generadora de bloque hadoop
 *
 * @author César Pomar
 */
public final class GenEspHadoop extends GenEspecial {

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenEspHadoop(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(Bloque s) {
        List<Bloque> lista = Buscar.buscarClases(s, Bloque.class);
        Bloque hadoop = null;
        for (Bloque b : lista) {
            if (b.getLlaveI().getEtiquetas() instanceof TagsHadoopApi) {
                hadoop = b;
            }
        }
        if (hadoop.getLlaveI().getEtiquetas() instanceof TagsMapper) {
            mapper(s, hadoop);
        } else {
            Bloque combine = null;
            Bloque reduction = null;
            for (Bloque b : lista) {
                if (b.getLlaveI().getEtiquetas() instanceof TagsBloque) {
                    String nombre = ((TagsBloque) b.getLlaveI().getEtiquetas()).getEtiqueta().getValor();
                    if (nombre.equals("<combine>")) {
                        combine = b;
                    } else if (nombre.equals("<reduction>")) {
                        reduction = b;
                    }
                    if (combine != null && reduction != null) {
                        break;
                    }
                }
            }
            reduccer(s, hadoop, combine, reduction);
        }
    }

    /**
     * Genera el mapper
     *
     * @param hadoop Bloque hadoop
     * @param mapper Bloque mapper
     */
    private void mapper(Bloque hadoop, Bloque mapper) {
        throw new UnsupportedOperationException("Hadoop Mapper, Not supported yet.");
    }

    /**
     * Genera el reduccer
     *
     * @param hadoop Bloque hadoop
     * @param reducer Bloque reducer
     * @param combine Bloque combine
     * @param reduction Bloque reduction
     */
    private void reduccer(Bloque hadoop, Bloque reducer, Bloque combine, Bloque reduction) {
        throw new UnsupportedOperationException("Hadoop Reduccer, Not supported yet.");
    }

}
