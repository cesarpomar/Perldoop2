package perldoop.jar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

/**
 * Clase para generar una libreria jar con las dependencias para la ejecución
 *
 * @author César Pomar
 */
public final class LibJar {

    //Paquetes a copiar en la libreria
    private static final String[] PAQUETES = {"perldoop/lib/", "jregex/", "org/jtr/"};
    //Clases que representan a las librerias a copiar
    private static final Class[] LIBRERIAS = {perldoop.lib.Box.class, jregex.Pattern.class, org.jtr.transliterate.Perl5Parser.class};
    //Nombre de la libreria
    private static final String NOMBRE = "Perldoop2-lib.jar";

    /**
     * Comprueba si la entrada pertenece a un paquete que es necesario copiar
     *
     * @param entry Entrada
     * @return Es necesario copiar
     */
    private static boolean isRequired(String entry) {
        for (String paquete : PAQUETES) {
            if (entry.startsWith(paquete)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Copia el contenido de un jar dentro del buffer
     *
     * @param jar Jar origen
     * @param jos Buffer destino
     * @throws IOException Error escritura
     */
    private static void copyJar(File jar, JarOutputStream jos) throws IOException {
        try (FileInputStream fis = new FileInputStream(jar);
                BufferedInputStream bis = new BufferedInputStream(fis);
                JarInputStream jis = new JarInputStream(bis);) {
            JarEntry je;
            while ((je = jis.getNextJarEntry()) != null) {
                if (isRequired(je.getName())) {
                    jos.putNextEntry(je);
                    byte[] buffer = new byte[10000];
                    int len;
                    while ((len = jis.read(buffer)) > 0) {
                        jos.write(buffer, 0, len);
                    }
                    jos.closeEntry();
                }
            }
        }
    }

    /**
     * Crea la libreria auxiliar con las clases minimas para la ejecución
     *
     * @param out Carpeta para guardar la libreria
     * @throws IOException Error de Escritura
     */
    public static void buildLib(File out) throws IOException {
        File fichero = new File(out, NOMBRE);
        Set<String> paths = new HashSet<>(10);
        for (Class cl : LIBRERIAS) {
            paths.add(cl.getProtectionDomain().getCodeSource().getLocation().getPath());
        }
        try (FileOutputStream fos = new FileOutputStream(fichero);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                JarOutputStream jos = new JarOutputStream(bos);) {
            for(String path:paths){
                File jar = new File(path);
                if(jar.isFile()){
                    copyJar(jar, jos);
                }
            }
        }
    }

}
