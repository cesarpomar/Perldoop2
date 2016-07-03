package perldoop.io;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import perldoop.modelo.generacion.BloqueJava;
import perldoop.modelo.generacion.ClaseJava;
import perldoop.modelo.generacion.CodigoJava;
import perldoop.modelo.generacion.SentenciaJava;

/**
 * Clase para la impresion de las traducciones
 *
 * @author César Pomar
 */
public class CodeWriter implements Closeable {

    private File directorio;
    private FileWriter escritura;
    private BufferedWriter buffer;
    private PrintWriter printer;
    private ClaseJava java;

    /**
     * Crea un directorio para escribir el codigo fuente
     *
     * @param directorio Directorio de salida
     */
    public CodeWriter(String directorio) {
        this.directorio = new File(directorio);
    }

    /**
     * Escribe la clase java en el directorio
     *
     * @param java Clase java
     * @param fichero Nombre del fichero
     * @throws IOException Error de escritura
     */
    public void escribir(ClaseJava java, String fichero) throws IOException {
        escritura = new FileWriter(fichero);
        buffer = new BufferedWriter(buffer);
        printer = new PrintWriter(buffer);
        this.java = java;
        paquetes();
        imports();
        clase();
        interfaces();
        clasepadre();
        bloques();
        printer.close();
        escritura.close();
    }

    private void paquetes() throws IOException {
        if (java.getPaquetes().isEmpty()) {
            return;
        }
        Iterator<String> it = java.getPaquetes().iterator();
        printer.append("package ").append(it.next());
        while (it.hasNext()) {
            printer.append(".").append(it.next());
        }
        printer.append(";");
        printer.println();
        printer.println();
    }

    private void imports() throws IOException {
        for (String i : java.getImports()) {
            printer.append("import ").append(i).append(";").println();
        }
        printer.println();
    }

    private void clase() throws IOException {
        printer.append("/*").println();
        printer.append(" *").println();
        printer.append(" */").println();
        printer.append("public class ").append(java.getNombre()).append(" ");
    }

    private void interfaces() throws IOException {
        if (java.getImplementar().isEmpty()) {
            return;
        }
        Iterator<String> it = java.getImplementar().iterator();
        printer.append("implements ").append(it.next()).append(" ");
        while (it.hasNext()) {
            printer.append(", ").append(it.next()).append(" ");
        }

    }

    private void clasepadre() throws IOException {
        if (java.getHeredar() == null) {
            return;
        }
        printer.append("extends ").append(java.getHeredar()).append(" ");
    }

    private void bloques() throws IOException {
        List<BloqueJava> pilaB = new ArrayList<>(20);
        List<Iterator<CodigoJava>> pilaI = new ArrayList<>(20);
        pilaB.add(java.getCodigo());
        pilaI.add(java.getCodigo().getCodigo().iterator());
        while (!pilaB.isEmpty()) {
            Iterator<CodigoJava> itc = pilaI.get(pilaI.size() - 1);
            if (!itc.hasNext()) {
                pilaI.remove(pilaI.size() - 1);
                printer.append(pilaB.remove(pilaB.size()-1).getPie()).println();
            }
            CodigoJava c = itc.next();
            if (c instanceof SentenciaJava) {
                printer.append(c.toString()).println();
            } else {
                BloqueJava b =(BloqueJava) c;
                pilaB.add(b);
                pilaI.add(b.getCodigo().iterator());
                printer.append(b.getCabecera()).println();
            }
        }
    }

    @Override
    public void close() throws IOException {
        buffer.close();
        escritura.close();
    }

}
