package perldoop.generacion.bloque.especial;

import perldoop.generacion.asignacion.GenIgual;
import perldoop.generacion.sentencia.GenSentencia;
import perldoop.generacion.util.Hadoop;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.arbol.bloque.BloqueForeachVar;
import perldoop.modelo.arbol.bloque.BloqueWhile;
import perldoop.modelo.arbol.expresion.ExpAsignacion;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.preprocesador.hadoop.TagsMapper;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Clase generadora de bloque hadoop
 *
 * @author César Pomar
 */
public final class GenEspMapper extends GenEspecial {

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenEspMapper(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(Bloque s) {
        StringBuilder codigo = new StringBuilder(1000);
        cabecera(s, codigo);
        Simbolo key = Hadoop.casting("pd_value", new Tipo(Tipo.STRING));
        Simbolo var;
        if (s instanceof BloqueForeachVar) {
            var = ((BloqueForeachVar) s).getVariable();
        } else {
            var = ((ExpAsignacion) Buscar.getExpresion(((BloqueWhile) s).getExpresion())).getAsignacion().getIzquierda();
        }
        codigo.append(GenIgual.asignacion(var, "", key)).append(';');
        codigo.append(s.getCuerpo());
        codigo.append('}');
        tabla.getClase().getFunciones().add(codigo);
        s.setCodigoGenerado(new StringBuilder(0));
    }

    /**
     * Genera la cabecera del mapper
     *
     * @param b Bloque
     * @param codigo Codigo
     */
    private void cabecera(Bloque b, StringBuilder codigo) {
        TagsMapper tags = (TagsMapper) b.getLlaveI().getEtiquetas();
        String key = Hadoop.hadoop(tags.getKeyOut());
        String value = Hadoop.hadoop(tags.getValueOut());
        tabla.getClase().setClasePadre("Mapper<Object, Text, " + key + ", " + value + ">");
        codigo.append("@Override ");
        codigo.append("public void map(Object pd_key, Text pd_value, Context pd_context) ");
        codigo.append("throws IOException, InterruptedException {");
        codigo.append(GenSentencia.genDeclaraciones(b, tabla));
        //Dependencias
        tabla.getClase().getImports().add("org.apache.hadoop.io.Text");
        tabla.getClase().getImports().add("org.apache.hadoop.io." + key);
        tabla.getClase().getImports().add("org.apache.hadoop.io." + value);
        tabla.getClase().getImports().add("org.apache.hadoop.mapreduce.Mapper");
        tabla.getClase().getImports().add("java.io.IOException");
    }

}
