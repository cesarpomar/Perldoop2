package perldoop.semantica.bloque.especial;

import java.util.ArrayList;
import java.util.List;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.arbol.bloque.BloqueForeachVar;
import perldoop.modelo.arbol.bloque.BloqueWhile;
import perldoop.modelo.preprocesador.TagsBloque;
import perldoop.modelo.preprocesador.hadoop.TagsHadoopApi;
import perldoop.modelo.preprocesador.hadoop.TagsMapper;
import perldoop.modelo.preprocesador.hadoop.TagsReduccer;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.util.Buscar;
import perldoop.util.Utiles;

/**
 * Clase para la semantica de bloque hadoop
 *
 * @author César Pomar
 */
public final class SemEspHadoop extends SemEspecial {

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemEspHadoop(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(Bloque s) {
        if (tabla.getClaseAttr().getPadre() != null) {
            //Error no se puede heredar
            throw new ExcepcionSemantica(null);
        }
        List<Bloque> lista = Buscar.buscarClases(s, Bloque.class);
        List<Bloque> etiquetados = new ArrayList<>();
        Bloque hadoop = null;
        for (Bloque b : lista) {
            if (b.getLlaveI().getEtiquetas() instanceof TagsHadoopApi) {
                if (hadoop != null) {
                    //Error mas de un bloque
                    throw new ExcepcionSemantica(null);
                }
                hadoop = b;
            } else if (b.getLlaveI().getEtiquetas() instanceof TagsBloque) {
                etiquetados.add(b);
            }
        }
        if (hadoop == null) {
            //Error bloque no encontrado
            throw new ExcepcionSemantica(null);
        }
        if (hadoop.getLlaveI().getEtiquetas() instanceof TagsMapper) {
            mapper(hadoop, hadoop, etiquetados);
        } else {
            reduccer(hadoop, hadoop, etiquetados);
        }
    }

    /**
     * Semantica para el mapper
     *
     * @param hadoop Bloque hadoop
     * @param mapper Bloque Mapper
     * @param bloques Bloques etiquetados
     */
    private void mapper(Bloque hadoop, Bloque mapper, List<Bloque> bloques) {
        tabla.getClaseAttr().setPadre("Hadoop_Mapper");
        if (mapper instanceof BloqueWhile && Buscar.getExpresion(((BloqueWhile) mapper).getExpresion()).getValor() instanceof Igual) {
            return;
        }
        if (mapper instanceof BloqueForeachVar) {
            return;
        }
        //Error bloque no admitido
        throw new ExcepcionSemantica(null);
    }

    /**
     * Semantica para el reduccer
     *
     * @param hadoop Bloque hadoop
     * @param reduccer Bloque Reduccer
     * @param bloques Bloques etiquetados
     */
    private void reduccer(Bloque hadoop, Bloque reduccer, List<Bloque> bloques) {
        tabla.getClaseAttr().setPadre("Hadoop_Reduccer");
        TagsReduccer tags = (TagsReduccer) reduccer.getLlaveI().getEtiquetas();
        Bloque combine = null;
        Bloque reduction = null;
        for (Bloque b : bloques) {
            String nombre = ((TagsBloque) b.getLlaveI().getEtiquetas()).getEtiqueta().getValor();
            if (nombre.equals("<combine>")) {
                if (combine != null) {
                    //Error repetido
                    throw new ExcepcionSemantica(null);
                }
                combine = b;
            } else if (nombre.equals("<reduction>")) {
                if (reduction != null) {
                    //Error repetido
                    throw new ExcepcionSemantica(null);
                }
                reduction = b;
            }
        }
        if (combine == null || reduction == null) {
            //error reducer incompleto
            throw new ExcepcionSemantica(null);
        }
        String var = Utiles.substring(tags.getVarKey().getValor(), 1, -1);
        if (tabla.getTablaSimbolos().buscarVariable(var, '$') == null) {
            //Variable no existe
            throw new ExcepcionSemantica(null);
        }
        var = Utiles.substring(tags.getVarValue().getValor(), 1, -1);
        if (tabla.getTablaSimbolos().buscarVariable(var, '$') == null) {
            //Variable no existe
            throw new ExcepcionSemantica(null);
        }
    }

}
