package perldoop.generacion.modulos;

import java.util.Arrays;
import java.util.List;
import perldoop.modelo.arbol.modulos.ModuloPackage;
import perldoop.modelo.arbol.modulos.ModuloUse;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Paquete;

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
        s.setCodigoGenerado(new StringBuilder(0));
        String nombreClase = s.getPaquetes().getClaseJava();
        nombreClase = tabla.getGestorReservas().getAlias(nombreClase, false);
        tabla.getClase().setNombre(nombreClase);
        tabla.getTablaSimbolos().getPaquete().setAlias(nombreClase);
        List<String> paquete = Arrays.asList(tabla.getTablaSimbolos().getArbolPaquete().getDirectorios(tabla.getGestorErrores().getFichero()));
        tabla.getClase().getPaquetes().addAll(paquete);
    }

    public void visitar(ModuloUse s) {
        s.setCodigoGenerado(new StringBuilder(0));
        if (s.getPaquetes().size() == 1) {
            //Si solo hay un identificador estan en el mismo paquete y pude omitirse la sentencia import
            return;
        }
        String fichero = tabla.getGestorErrores().getFichero();
        String[] paquetes = s.getPaquetes().getArrayString();
        Paquete paquete = tabla.getTablaSimbolos().getPaquete(fichero, paquetes);
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("import ");
        codigo.append(paquete.getRuta());
        codigo.append(s.getPuntoComa());
        tabla.getClase().getImports().add(codigo.toString());
    }

}
