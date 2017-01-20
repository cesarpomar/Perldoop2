package perldoop.modelo.semantica;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import perldoop.util.Utiles;

/**
 * Clase que administra el directorio de paquetes donde se almacenara cada clase
 *
 * @author César Pomar
 */
public final class ArbolPaquetes {

    private List<File> ficheros;
    private Map<String, Paquete> paquetes;
    private Map<String, String> clases;
    private Map<String, String[]> directorios;
    private String[] paquetesUsuario;

    /**
     * Contructor unico de la clase
     *
     * @param ficheros Lista de ficheros a analizar
     * @param paquetesUsuario Paquetes definidos por el usuario
     */
    public ArbolPaquetes(List<File> ficheros,String[] paquetesUsuario) {
        this.ficheros = ficheros;
        this.paquetesUsuario = paquetesUsuario;
        directorios = new HashMap<>(ficheros.size());
        clases = new HashMap<>(ficheros.size());
        paquetes = new HashMap<>(ficheros.size());
        calcularPaquetes();      
    }

    /**
     * Calcula el arbol de paquetes
     */
    private void calcularPaquetes() {
        List<List<String>> paths = new ArrayList<>(ficheros.size());
        HashMap<String, String> roots = new HashMap<>(10);
        int root = 0;
        int min = -1;
        //partir ruta en carpetas
        for (File fichero : ficheros) {
            File ruta = fichero.getAbsoluteFile();
            clases.put(fichero.getPath(), ruta.getName().substring(0, ruta.getName().lastIndexOf(".")));
            Path path = ruta.getParentFile().toPath();
            List<String> carpetas = new ArrayList<>(20);
            //Renombramos el root(/ o x:) con un valor valido
            String carpeta = roots.get(path.getRoot().toString());
            if (carpeta == null) {
                carpeta = "root" + root++;
                roots.put(path.getRoot().toString(), carpeta);
            }
            carpetas.add(carpeta);
            for (Path p : path.toAbsolutePath()) {
                carpetas.add(Utiles.normalizar(p.toString()));
            }
            paths.add(carpetas);
            if (min == -1 || min > carpetas.size()) {
                min = carpetas.size();
            }
        }
        //Calcular maxima carpeta comun
        int deep = -1;
        FOR:
        for (int i = 0; i < min; i++) {
            String carpeta = paths.get(0).get(i);
            for (List<String> carpetas : paths) {
                if (!carpetas.get(i).equals(carpeta)) {
                    break FOR;
                }
            }
            deep++;
        }
        //Almacenar la ruta relativa de cada una
        for (int i = 0; i < paths.size(); i++) {
            List<String> carpetas = paths.get(i);
            if (deep == -1) {
                carpetas.add(0, "root");
            } else {
                carpetas = carpetas.subList(deep, carpetas.size());
            }
            //Cambia el paquete padre por el definido por el usuario
            if(paquetesUsuario!=null){
                carpetas.remove(0);
                carpetas.addAll(0, Arrays.asList(paquetesUsuario));
            }
            directorios.put(ficheros.get(i).getPath(), carpetas.toArray(new String[carpetas.size()]));
        }
    }

    /**
     * Obtiene la ruta de directorios para un paquete
     *
     * @param ruta Ruta del fichero
     * @return Ruta
     */
    public String[] getDirectorios(String ruta) {
        return directorios.get(ruta);
    }

    /**
     * Obtiene los nombres de las clases asociadas a cada fichero
     *
     * @return Nombres de las clases
     */
    public Map<String, String> getClases() {
        return clases;
    }

    /**
     * Obtiene todos los paquetes
     *
     * @return paquetes
     */
    public Map<String, Paquete> getPaquetes() {
        return paquetes;
    }

}
