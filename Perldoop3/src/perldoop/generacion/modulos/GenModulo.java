package perldoop.generacion.modulos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        List<String> paquetes = new ArrayList<>(20);
        String clase = s.getPaquetes().getIdentificadores().get(s.getPaquetes().size()-1).getValor();
        paquetes.addAll(Arrays.asList(tabla.getTablaSimbolos().getArbolPaquete().getDirectorios(tabla.getGestorErrores().getFichero())));      
        paquetes.addAll(Arrays.asList(Arrays.copyOf(s.getPaquetes().getArrayString(), s.getPaquetes().size()-1)));
        paquetes.add(tabla.getTablaSimbolos().getImports().get(clase).getAlias());
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("import ");
        codigo.append(String.join(".", paquetes));
        codigo.append(s.getPuntoComa());
        tabla.getClase().getImports().add(codigo.toString());
    }

}
