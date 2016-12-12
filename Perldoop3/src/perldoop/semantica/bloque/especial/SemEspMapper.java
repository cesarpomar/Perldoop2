package perldoop.semantica.bloque.especial;

import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.arbol.bloque.BloqueForeachVar;
import perldoop.modelo.arbol.bloque.BloqueWhile;
import perldoop.modelo.preprocesador.TagsBloque;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.util.Buscar;

/**
 * Clase para la semantica de bloque hadoop
 *
 * @author César Pomar
 */
public final class SemEspMapper extends SemEspecial {

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemEspMapper(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(Bloque s) {
        checkGlobal(s);
        checkFlujo(s);
        if (tabla.getClaseAttr().getPadre() != null) {
            tabla.getGestorErrores().error(Errores.BLOQUE_ESP_EN_USO, ((TagsBloque) s.getLlaveI().getEtiquetas()).getEtiqueta(), tabla.getClaseAttr().getPadre());
            throw new ExcepcionSemantica(Errores.BLOQUE_ESP_EN_USO);
        }
        tabla.getClaseAttr().setPadre("Hadoop_Mapper");
        if (s instanceof BloqueWhile && Buscar.getExpresion(((BloqueWhile) s).getExpresion()).getValor() instanceof Igual) {
            return;
        }
        if (s instanceof BloqueForeachVar) {
            return;
        }
        tabla.getGestorErrores().error(Errores.BLOQUE_INCOMPATIBLE, ((TagsBloque) s.getLlaveI().getEtiquetas()).getEtiqueta());
        throw new ExcepcionSemantica(Errores.BLOQUE_INCOMPATIBLE);
    }

}
