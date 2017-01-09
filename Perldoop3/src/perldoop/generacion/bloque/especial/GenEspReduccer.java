package perldoop.generacion.bloque.especial;

import perldoop.generacion.sentencia.GenSentencia;
import perldoop.generacion.util.Hadoop;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.arbol.sentencia.Sentencia;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.preprocesador.TagsBloque;
import perldoop.modelo.preprocesador.hadoop.TagsReduccer;
import perldoop.util.Buscar;
import perldoop.util.Utiles;

/**
 * Clase generadora de bloque hadoop
 *
 * @author César Pomar
 */
public final class GenEspReduccer extends GenEspecial {

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenEspReduccer(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(Bloque s) {
        StringBuilder codigo = new StringBuilder(1000);
        cabecera(s, codigo);
        //Buscar los bloques
        Bloque combine = null;
        Bloque reduction = null;
        for (Bloque b : Buscar.buscarClases(s, Bloque.class)) {
            TagsBloque tags = (TagsBloque) b.getLlaveI().getEtiquetas();
            if (tags != null) {
                String nombre = tags.getEtiqueta().getValor();
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
        //Copiamos sentencias
        for (Sentencia stc : s.getCuerpo().getSentencias()) {
            if (Buscar.tokenInicio(stc).getValor().equals("my")) {
                codigo.append(stc);
            }
        }
        //Combinacion
        TagsReduccer tags = (TagsReduccer) s.getLlaveI().getEtiquetas();
        String var = Utiles.substring(tags.getVarKey().getValor(), 2, -1);
        codigo.append(var).append("= pd_key;");
        codigo.append("while(pd_values.hashNext()){");
        tags = (TagsReduccer) s.getLlaveI().getEtiquetas();
        var = Utiles.substring(tags.getVarKey().getValor(), 2, -1);
        codigo.append(var).append("= pd_values.next();");
        codigo.append(combine.getCuerpo());
        codigo.append('}');
        //reduccion
        codigo.append(reduction.getCuerpo());
        codigo.append('}');
        tabla.getClase().getFunciones().add(codigo);
        s.setCodigoGenerado(new StringBuilder(0));
    }

    /**
     * Genera la cabecera del reduccer
     *
     * @param b Bloque
     * @param codigo Codigo
     */
    private void cabecera(Bloque b, StringBuilder codigo) {
        TagsReduccer tags = (TagsReduccer) b.getLlaveI().getEtiquetas();
        String keyIn = Hadoop.hadoop(tags.getKeyOut());
        String valueIn = Hadoop.hadoop(tags.getValueOut());
        String keyOut = Hadoop.hadoop(tags.getKeyOut());
        String valueOut = Hadoop.hadoop(tags.getValueOut());
        tabla.getClase().setClasePadre("Reducer<" + keyIn + ", " + valueIn + ", " + keyOut + ", " + valueOut + ">");
        codigo.append("@Override ");
        codigo.append("public void reduce(").append(keyIn).append(" pd_key, Iterator<").append(valueIn);
        codigo.append("> pd_values, Context pd_context) throws IOException, InterruptedException {");
        codigo.append(GenSentencia.genDeclaraciones(b, tabla));
        //Dependencias
        tabla.getClase().getImports().add("org.apache.hadoop.io." + keyIn);
        tabla.getClase().getImports().add("org.apache.hadoop.io." + valueIn);
        tabla.getClase().getImports().add("org.apache.hadoop.io." + keyOut);
        tabla.getClase().getImports().add("org.apache.hadoop.io." + valueOut);
        tabla.getClase().getImports().add("org.apache.hadoop.mapreduce.Reduccer");
        tabla.getClase().getImports().add("java.io.IOException");
        tabla.getClase().getImports().add("java.util.Iterator");
    }

}
