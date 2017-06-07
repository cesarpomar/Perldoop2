package perldoop.generacion.funciondef;

import java.util.List;
import java.util.ListIterator;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.arbol.bloque.BloqueDoUntil;
import perldoop.modelo.arbol.bloque.BloqueDoWhile;
import perldoop.modelo.arbol.bloque.BloqueIf;
import perldoop.modelo.arbol.bloque.BloqueSimple;
import perldoop.modelo.arbol.bloque.BloqueUnless;
import perldoop.modelo.arbol.bloque.SubBloqueElse;
import perldoop.modelo.arbol.bloque.SubBloqueElsif;
import perldoop.modelo.arbol.flujo.Return;
import perldoop.modelo.arbol.funciondef.FuncionDef;
import perldoop.modelo.arbol.modificador.ModNada;
import perldoop.modelo.arbol.sentencia.Sentencia;
import perldoop.modelo.arbol.sentencia.StcFlujo;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.preprocesador.storm.TagsStorm;
import perldoop.util.Buscar;
import perldoop.util.Utiles;

/**
 * Clase generadora de funcionDef
 *
 * @author César Pomar
 */
public class GenFuncionDef {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenFuncionDef(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(FuncionDef s) {
        StringBuilder codigo = new StringBuilder(s.getCuerpo().getCodigoGenerado().length() + 50);
        codigo.append(s.getFuncionSub().getCodigoGenerado());
        codigo.append(s.getLlaveI().getCodigoGenerado());
        codigo.append(s.getCuerpo().getCodigoGenerado());
        codigo.append(genReturn(s));
        codigo.append(s.getLlaveD());
        s.setCodigoGenerado(codigo);
        if (s.getLlaveI().getEtiquetas() != null && s.getLlaveI().getEtiquetas() instanceof TagsStorm) {
            stormBolt(s);
        }
    }

    /**
     * Comprueba que la funcion siempre genere un return
     *
     * @param f Funcion
     * @return Codigo return
     */
    private String genReturn(FuncionDef f) {
        List<Sentencia> sentencias = f.getCuerpo().getSentencias();
        if (!sentencias.isEmpty()) {
            //Asumiendo que el codigo inalcanzable ya ha sido comprobado, buscamos retorno en la ultima sentencia
            List<Return> retornos = Buscar.buscarClases(sentencias.get(sentencias.size() - 1), Return.class);
            ListIterator<Return> it = retornos.listIterator(retornos.size());
            Simbolo condicion = null;
            WHILE:
            while (it.hasPrevious()) {
                Return r = it.previous();
                Simbolo padre = r.getPadre();
                //Si tiene un modificador ya no es seguro su ejecución
                if (!(((StcFlujo) padre).getModificador() instanceof ModNada)) {
                    continue;
                }
                while (padre != f) {
                    //Si el padre es el bloque condicional, podemos ascender
                    if (padre.getPadre() == condicion) {
                        padre = condicion.getPadre();
                        continue;
                    }
                    //Si es un bloque condicional lo almacenamos en espera del superbloque
                    if (padre instanceof BloqueIf || padre instanceof BloqueUnless || padre instanceof SubBloqueElsif || padre instanceof SubBloqueElse) {
                        condicion = padre.getPadre();
                    }
                    //Si el bloque depende de una condicion, se asume que no se ejecutara
                    if (padre instanceof Bloque && !(padre instanceof BloqueSimple) && !(padre instanceof BloqueDoUntil) && !(padre instanceof BloqueDoWhile)) {
                        continue WHILE;
                    }
                    padre = padre.getPadre();
                }
                return "";
            }
        }
        return "return new Box[0];";
    }

    /**
     * Genera una clase bolt con la funcion si contiene las etiquetas de storm
     *
     * @param f Funcion
     */
    private void stormBolt(FuncionDef f) {
        TagsStorm ts = (TagsStorm) f.getLlaveI().getEtiquetas();
        String input = Utiles.substring(ts.getInput().getValor(), 2, -1);
        String output = Utiles.substring(ts.getOutput().getValor(), 2, -1);
        String name = f.getFuncionSub().getId().getValor();
        tabla.getClase().getImports().add("org.apache.storm.topology.base.BaseBasicBolt");
        tabla.getClase().getImports().add("backtype.storm.topology.OutputFieldsDeclarer");
        tabla.getClase().getImports().add("org.apache.storm.topology.BasicOutputCollector");
        tabla.getClase().getImports().add("backtype.storm.tuple.Fields");
        tabla.getClase().getImports().add("backtype.storm.tuple.Tuple");
        tabla.getClase().getImports().add("backtype.storm.tuple.Values");
        tabla.getClase().getImports().add("java.util.Map");
        tabla.getClase().getImports().add("java.util.List");
        tabla.getClase().getImports().add("org.apache.storm.task.TopologyContext");
        tabla.getClase().setClasePadre("BaseBasicBolt");

        StringBuilder metodo = new StringBuilder(100);
        metodo.append("@Override ");
        metodo.append("public void prepare(Map stormConf, TopologyContext context){");
        metodo.append("/*Empty, put your code here*/");
        metodo.append("}");
        tabla.getClase().getFunciones().add(metodo);

        metodo = new StringBuilder(100);
        metodo.append("@Override ");
        metodo.append("public void cleanup(){");
        metodo.append("/*Empty, put your code here*/");
        metodo.append("}");
        tabla.getClase().getFunciones().add(metodo);

        metodo = new StringBuilder(100);
        metodo.append("@Override ");
        metodo.append("public void declareOutputFields(OutputFieldsDeclarer ofd) {");
        metodo.append("ofd.declareStream(").append('"').append(output).append('"').append(',');
        metodo.append("new Fields(").append('"').append(output).append('"').append("));");
        metodo.append("}");
        tabla.getClase().getFunciones().add(metodo);

        metodo = new StringBuilder(100);
        metodo.append("@Override ");
        metodo.append("public void execute(Tuple tuple, BasicOutputCollector boc) {");
        metodo.append("List<String> input=(List)tuple.getValueByField(").append('"').append(input).append('"').append(");");
        metodo.append("input = new PerlList<>(input);");
        metodo.append("List<String> output=(List)");
        metodo.append(name).append("(new Box[]{Casting.box(new Ref(input))})[0].refValue().get();");
        metodo.append("boc.emit(").append('"').append(output).append('"').append(", new Values(output));");
        metodo.append("}");
        tabla.getClase().getFunciones().add(metodo);
    }

}
