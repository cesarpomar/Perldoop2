package perldoop.generacion.funcion.nativa;

import java.util.Arrays;
import perldoop.generacion.util.ColIterator;
import perldoop.generacion.util.Hadoop;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.preprocesador.hadoop.TagsHadoopApi;
import perldoop.modelo.preprocesador.hadoop.TagsMapper;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;
import perldoop.util.ParserEtiquetas;

/**
 * Generador de la funcion print para bloques Mapper o Reduccer
 *
 * @author César Pomar
 */
public class GenFuncionEspPrint extends GenFuncionNativa {

    public GenFuncionEspPrint(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(FuncionBasica f) {
        StringBuilder codigo = new StringBuilder(100);
        Bloque b = Buscar.getSpecial(f, TagsHadoopApi.class);
        TagsHadoopApi tags = (TagsHadoopApi) b.getLlaveI().getEtiquetas();
        Tipo tKey = tags.getKeyOut() == null ? new Tipo(Tipo.STRING) : ParserEtiquetas.parseTipo(Arrays.asList(tags.getKeyOut()));
        Tipo tValue = tags.getValueOut() == null ? new Tipo(Tipo.STRING) : ParserEtiquetas.parseTipo(Arrays.asList(tags.getValueOut()));
        codigo.append(f.getIdentificador().getComentario());
        if (tags instanceof TagsMapper) {
            codigo.append("pd_context.write(");
        } else {
            codigo.append("pd_collector.collect(");
        }
        ColIterator it = new ColIterator(f.getColeccion());
        codigo.append(it.getComentario());
        codigo.append(Hadoop.casting(it.next(), tKey));
        codigo.append(it.getComentario());
        codigo.append(",");
        it.next();
        codigo.append(it.getComentario());
        codigo.append(Hadoop.casting(it.next(), tValue));
        codigo.append(it.getComentario());
        while (it.hasNext()) {
            it.next();
            codigo.append(it.getComentario());
        }
        codigo.append(")");
        f.setCodigoGenerado(codigo);
    }

}
