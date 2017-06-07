package perldoop.semantica.funciondef;

import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.funciondef.FuncionDef;
import perldoop.modelo.preprocesador.TagsBloque;
import perldoop.modelo.preprocesador.storm.TagsStorm;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de funcionDef
 *
 * @author César Pomar
 */
public class SemFuncionDef {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemFuncionDef(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(FuncionDef s) {
        tabla.getTablaSimbolos().cerrarBloque();
        if (s.getLlaveI().getEtiquetas() != null && s.getLlaveI().getEtiquetas() instanceof TagsStorm) {
            stormBolt(s);
        }
    }

    /**
     * Comprueba una clase bolt con la funcion si contiene las etiquetas de storm
     *
     * @param f Funcion
     */
    private void stormBolt(FuncionDef f) {
        if (tabla.getClaseAttr().getPadre() != null) {
            tabla.getGestorErrores().error(Errores.BLOQUE_ESP_EN_USO, ((TagsBloque) f.getLlaveI().getEtiquetas()).getEtiqueta(), tabla.getClaseAttr().getPadre());
            throw new ExcepcionSemantica(Errores.BLOQUE_ESP_EN_USO);
        }
        tabla.getClaseAttr().setPadre("Storm");
    }
}
