package perldoop.semantica.modulos;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.modulos.ModuloDo;
import perldoop.modelo.arbol.modulos.ModuloPackage;
import perldoop.modelo.arbol.modulos.ModuloUse;
import perldoop.modelo.semantica.Paquete;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.util.Buscar;

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
        if (!tabla.getTablaSimbolos().isVacia()) {
            tabla.getGestorErrores().error(Errores.MODULO_NO_VACIO, s.getIdPackage().getToken());
            throw new ExcepcionSemantica(Errores.MODULO_NO_VACIO);
        }
        if (tabla.getTablaSimbolos().getPaquete() != null) {
            tabla.getGestorErrores().error(Errores.MODULO_YA_CREADO, s.getIdPackage().getToken());
            throw new ExcepcionSemantica(Errores.MODULO_YA_CREADO);
        }
        tabla.getTablaSimbolos().crearPaquete(tabla.getGestorErrores().getFichero());
        tabla.getTablaSimbolos().getPaquete().setIdentificador(s.getPaquetes().getClaseJava());
    }

    public void visitar(ModuloUse s) {
        List<Terminal> ids = s.getPaquetes().getIdentificadores();
        String clase = ids.get(ids.size() - 1).getValor();
        String fichero = tabla.getGestorErrores().getFichero();
        String[] paquetes = s.getPaquetes().getArrayString();
        Paquete paquete = tabla.getTablaSimbolos().getPaquete(fichero, paquetes, 0);
        if (paquete == null) {
            if (tabla.getAcciones().detenerTraductor()) {
                return;
            }
            tabla.getGestorErrores().error(Errores.MODULO_NO_EXISTE, s.getPuntoComa().getToken());
            throw new ExcepcionSemantica(Errores.MODULO_NO_EXISTE);
        }
        tabla.getTablaSimbolos().getImports().put(clase, paquete);
    }

    public void visitar(ModuloDo s) {
        List<Simbolo> elems = s.getCadena().getTexto().getElementos();
        if (elems.size() != 1 || !(elems.get(0) instanceof Terminal)) {
            tabla.getGestorErrores().error(Errores.IMPORT_DINAMICO, Buscar.tokenInicio(elems.get(1)));
            throw new ExcepcionSemantica(Errores.IMPORT_DINAMICO);
        }
        File file = new File(((Terminal) elems.get(0)).getValor());
        if (file.isAbsolute()) {
            tabla.getGestorErrores().error(Errores.IMPORT_ABSOLUTO, Buscar.tokenInicio(elems.get(0)));
            throw new ExcepcionSemantica(Errores.IMPORT_ABSOLUTO);
        }
        List<String> ruta = new ArrayList<>();
        int subirDirectorio = 0;
        String fichero = tabla.getGestorErrores().getFichero();
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
        Collections.reverse(ruta);
        Paquete paquete = tabla.getTablaSimbolos().getPaquete(fichero, ruta.toArray(new String[ruta.size()]), subirDirectorio);
        if (paquete == null) {
            if (tabla.getAcciones().detenerTraductor()) {
                return;
            }
            tabla.getGestorErrores().error(Errores.MODULO_NO_EXISTE, s.getPuntoComa().getToken());
            throw new ExcepcionSemantica(Errores.MODULO_NO_EXISTE);
        }
        tabla.getTablaSimbolos().getImports().put(paquete.getIdentificador(), paquete);
    }

}
