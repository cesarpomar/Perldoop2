package perldoop.io;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import perldoop.modelo.Opciones;
import perldoop.modelo.generacion.ClaseJava;

/**
 * Clase para la impresion de las traducciones
 *
 * @author César Pomar
 */
public class CodeWriter {

    private File directorio;
    private Opciones opciones;

    /**
     * Crea un directorio para escribir el codigo fuente
     *
     * @param opciones Opciones
     */
    public CodeWriter(Opciones opciones) {
        this.opciones = opciones;
        this.directorio = opciones.getDirectorioSalida();
    }

    /**
     * Escribe la clase java en el directorio
     *
     * @param java Clase java
     * @throws IOException Error de escritura
     */
    public void escribir(ClaseJava java) throws IOException {
        StringBuilder repr = new StringBuilder(10000);
        //Paquete
        if (!java.getPaquetes().isEmpty()) {
            repr.append("package ");
            appendList(repr, java.getPaquetes(), ".");
            repr.append("; ");
        }
        //Imports
        appendList(repr, java.getImports());
        //Clase
        repr.append("public class ").append(java.getNombre()).append(" ");
        //Clase Padre
        if (java.getClasePadre() != null) {
            repr.append("extends ").append(java.getClasePadre()).append(" ");
        }
        //Interfaces
        if (!java.getInterfaces().isEmpty()) {
            repr.append("implements ");
            appendList(repr, java.getInterfaces(), ",");
            repr.append(" ");
        }
        //Inicio clase
        repr.append("{");
        //Atributos
        appendList(repr, java.getAtributos());
        //Funciones
        appendList(repr, java.getFunciones());
        //Codigo global
        repr.append("public static void global(){");
        appendList(repr, java.getCodigoGlobal());
        repr.append("}");
        //Fin clase
        repr.append("}");
        //Formatemos
        if (opciones.isFormatearCodigo()) {
            try {
                escribir(new Formatter().formatSource(repr.toString()), java.getNombre(), java.getPaquetes());
                return;
            } catch (FormatterException ex) {
                Logger.getLogger(CodeWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        escribir(repr.toString(), java.getNombre(), java.getPaquetes());
    }

    private void escribir(String codigo, String fichero, List<String> paquetes) throws IOException {
        File ruta = directorio;
        for (String p : paquetes) {
            ruta = new File(ruta, p);
        }
        ruta.mkdirs();
        try (FileWriter escritura = new FileWriter(new File(ruta, fichero + ".java"));
                BufferedWriter buffer = new BufferedWriter(escritura);) {
            buffer.append(codigo);
        }
    }

    /**
     * Inserta una colección en una cadena
     *
     * @param <T> Tipo de la colección
     * @param sb Cadenas
     * @param col Colección de cadenas
     * @param separador Separador
     */
    public <T> void appendList(StringBuilder sb, Collection<T> col, String separador) {
        Iterator<T> it = col.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(separador);
            }
        }
    }

    /**
     * Inserta una colección en una cadena
     *
     * @param <T> Tipo de la colección
     * @param sb Cadenas
     * @param col Colección de cadenas
     */
    public <T> void appendList(StringBuilder sb, Collection<T> col) {
        appendList(sb, col, "");
    }

}
