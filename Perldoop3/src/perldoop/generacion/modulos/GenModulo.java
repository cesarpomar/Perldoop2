package perldoop.generacion.modulos;

import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.modulos.ModuloPackage;
import perldoop.modelo.arbol.modulos.ModuloUse;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de modulo
 *
 * @author César Pomar
 */
public class GenModulo {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenModulo(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(ModuloPackage s) {
        tabla.getClase().setNombre(s.getId().toString());
        for (Terminal p : s.getPaquetes().getIdentificadores()) {
            tabla.getClase().getPaquetes().add(p.toString());
        }
        s.setCodigoGenerado(new StringBuilder(0));
    }

    public void visitar(ModuloUse s) {
    }

}
