package perldoop.generacion.modulos;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.modulos.ModuloDo;
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
        List<String> paquete = Arrays.asList(tabla.getTablaSimbolos().getArbolPaquete().getDirectorios(tabla.getFichero()));
        tabla.getClase().getPaquetes().addAll(paquete);
    }

    public void visitar(ModuloUse s) {
        s.setCodigoGenerado(new StringBuilder(0));
        if (s.getPaquetes().size() == 1) {
            //Si solo hay un identificador estan en el mismo paquete y pude omitirse la sentencia import
            return;
        }
        String fichero = tabla.getFichero();
        String[] paquetes = s.getPaquetes().getArrayString();
        Paquete paquete = tabla.getTablaSimbolos().getPaquete(fichero, paquetes, 0);
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("import ").append(s.getIdUse().getComentario());
        codigo.append(String.join(".", tabla.getTablaSimbolos().getArbolPaquete().getDirectorios(paquete.getFichero())));
        codigo.append('.').append(codigo).append(paquete.getAlias());
        codigo.append(s.getPuntoComa());
        tabla.getClase().getImports().add(codigo.toString());
    }

    public void visitar(ModuloDo s) {
        s.setCodigoGenerado(new StringBuilder(0));
        File file = new File(((Terminal) s.getCadena().getTexto().getElementos().get(0)).getValor());
        List<String> ruta = new ArrayList<>();
        int subirDirectorio = 0;
        String fichero = tabla.getFichero();
        String clase = file.getName().substring(0, file.getName().lastIndexOf("."));
        ruta.add(clase);
        while ((file = file.getParentFile()) != null) {
            String name = file.getName();
            if (name.equals("..")) {
                subirDirectorio++;
            } else if (name.contains(".")) {
                break;
            } else {
                ruta.add(file.getName());
            }
        }
        if (ruta.size() == 1) {
            //Si solo hay un identificador estan en el mismo paquete y pude omitirse la sentencia import
            return;
        }
        Collections.reverse(ruta);
        Paquete paquete = tabla.getTablaSimbolos().getPaquete(fichero, ruta.toArray(new String[ruta.size()]), subirDirectorio);
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("import ").append(s.getId().getComentario());
        codigo.append(String.join(".", tabla.getTablaSimbolos().getArbolPaquete().getDirectorios(paquete.getFichero())));
        codigo.append('.').append(paquete.getAlias());
        codigo.append(s.getPuntoComa());
        tabla.getClase().getImports().add(codigo.toString());
    }

}
