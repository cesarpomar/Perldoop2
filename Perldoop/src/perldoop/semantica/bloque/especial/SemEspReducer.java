package perldoop.semantica.bloque.especial;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.arbol.sentencia.Sentencia;
import perldoop.modelo.arbol.sentencia.StcBloque;
import perldoop.modelo.arbol.variable.VarMy;
import perldoop.modelo.arbol.variable.VarOur;
import perldoop.modelo.arbol.variable.Variable;
import perldoop.modelo.preprocesador.TagsBloque;
import perldoop.modelo.preprocesador.hadoop.TagsReducer;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.util.Buscar;
import perldoop.util.Utiles;

/**
 * Clase para la semantica de bloque hadoop
 *
 * @author César Pomar
 */
public final class SemEspReducer extends SemEspecial {

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemEspReducer(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(Bloque s) {
        if (tabla.getClaseAttr().getPadre() != null) {
            tabla.getGestorErrores().error(Errores.BLOQUE_ESP_EN_USO, ((TagsBloque) s.getLlaveI().getEtiquetas()).getEtiqueta(), tabla.getClaseAttr().getPadre());
            throw new ExcepcionSemantica(Errores.BLOQUE_ESP_EN_USO);
        }
        tabla.getClaseAttr().setPadre("Hadoop_Reducer");
        List<Bloque> bloques = Buscar.buscarClases(s, Bloque.class);
        Bloque combine = null;
        Bloque reduction = null;
        for (Bloque b : bloques) {
            TagsBloque tags = (TagsBloque) b.getLlaveI().getEtiquetas();
            if(tags==null){
                continue;
            }
            String nombre = tags.getEtiqueta().getValor();
            if (nombre.equals("<combine>")) {
                if (combine != null) {
                    tabla.getGestorErrores().error(Errores.ESPECIAL_REPETIDO, ((TagsBloque) b.getLlaveI().getEtiquetas()).getEtiqueta());
                    throw new ExcepcionSemantica(Errores.ESPECIAL_REPETIDO);
                }
                combine = b;
            } else if (nombre.equals("<reduction>")) {
                if (reduction != null) {
                    tabla.getGestorErrores().error(Errores.ESPECIAL_REPETIDO, ((TagsBloque) b.getLlaveI().getEtiquetas()).getEtiqueta());
                    throw new ExcepcionSemantica(Errores.ESPECIAL_REPETIDO);
                }
                reduction = b;
            }
        }
        if (combine == null || reduction == null) {
            tabla.getGestorErrores().error(Errores.REDUCER_INVALIDO, ((TagsBloque) s.getLlaveI().getEtiquetas()).getEtiqueta());
            throw new ExcepcionSemantica(Errores.REDUCER_INVALIDO);
        }
        TagsReducer tags = (TagsReducer) s.getLlaveI().getEtiquetas();
        String var = Utiles.substring(tags.getVarKey().getValor(), 2, -1);
        if (tabla.getTablaSimbolos().buscarVariable(var, '$') == null) {
            tabla.getGestorErrores().error(Errores.VARIABLE_NO_EXISTE, tags.getVarKey(), tags.getVarKey().getValor(), '$');
            throw new ExcepcionSemantica(Errores.VARIABLE_NO_EXISTE);
        }
        var = Utiles.substring(tags.getVarValue().getValor(), 2, -1);
        if (tabla.getTablaSimbolos().buscarVariable(var, '$') == null) {
            tabla.getGestorErrores().error(Errores.VARIABLE_NO_EXISTE, tags.getVarValue(), tags.getVarValue().getValor(), '$');
            throw new ExcepcionSemantica(Errores.VARIABLE_NO_EXISTE);
        }
        //Comprobacion de dependencias de combine y reduction
        Set<String> locales = new HashSet<>();
        for (Sentencia stc : s.getCuerpo().getSentencias()) {
            for (Variable v : Buscar.buscarClases(stc, Variable.class)) {
                if (v instanceof VarMy || v instanceof VarOur) {
                    locales.add(Buscar.getContexto(v) + v.getVar().getValor());
                }
            }
        }
        for (Bloque padre : new Bloque[]{combine, reduction}) {
            while ((padre = Buscar.buscarPadre(padre, Bloque.class)) != s) {
                for (Sentencia stc : padre.getCuerpo().getSentencias()) {
                    if (!(stc instanceof StcBloque)) {
                        List<Variable> vars = Buscar.buscarClases(stc, Variable.class);
                        for (Variable v : vars) {
                            if ((v instanceof VarMy || v instanceof VarOur) && locales.contains(Buscar.getContexto(v) + v.getVar().getValor())) {
                                tabla.getGestorErrores().error(Errores.REDUCER_DEPENDENCIAS, v.getContexto().getToken());
                                throw new ExcepcionSemantica(Errores.REDUCER_DEPENDENCIAS);
                            }
                        }
                    }
                }
            }

        }
        //Comprobamos si las dependencias estan satisfechas
        List<Variable> vars = Buscar.buscarDependencias(combine);
        vars.addAll(Buscar.buscarDependencias(s));
        for (Variable v : vars) {
            char c = Buscar.getContexto(v);
            String id = v.getVar().getValor();
            if (tabla.getTablaSimbolos().buscarVariable(id, c) == null && !locales.contains(c + id)) {
                tabla.getGestorErrores().error(Errores.REDUCER_DEPENDENCIAS, v.getContexto().getToken());
                throw new ExcepcionSemantica(Errores.REDUCER_DEPENDENCIAS);
            }
        }
    }

}
