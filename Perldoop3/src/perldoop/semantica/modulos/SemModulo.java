package perldoop.semantica.modulos;

import perldoop.modelo.arbol.modulos.ModuloPackage;
import perldoop.modelo.arbol.modulos.ModuloUse;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de modulos
 *
 * @author César Pomar
 */
public class SemModulo {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemModulo(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(ModuloPackage s) {
        if (tabla.getTablaSimbolos().isPaquete()) {
            //TODO Dar error paqute ya creado
        }
        if (!tabla.getTablaSimbolos().isVacia()) {
            //TODO Dar error paquete tiene que ser declarado al principio
        }
        tabla.getTablaSimbolos().crerPaquete(s.getPaquetes().getRepresentancion());
    }

    public void visitar(ModuloUse s) {
    }

}
