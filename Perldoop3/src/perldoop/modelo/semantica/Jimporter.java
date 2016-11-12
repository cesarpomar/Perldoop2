package perldoop.modelo.semantica;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import perldoop.lib.Box;

/**
 * Clase para importar codigo java desde el classpath siempre que sea compatible con el formato perldoop
 *
 * @author César Pomar
 */
public final class Jimporter {

    private static String ENV_VAR = "PERLDOOP";
    private static String DEFAULT = "imports";
    private static Loader loader = new Loader();
    private ArbolPaquetes paquetes;

    /**
     * Construye el importador
     *
     * @param paquetes Arbol de paquetes sobre el que realizar los imports
     */
    public Jimporter(ArbolPaquetes paquetes) {
        this.paquetes = paquetes;
    }

    /**
     * Importa los ficheros java al arbol de paquetes
     *
     * @param ruta Ruta del import jaava
     * @return Paquete importado
     */
    public Paquete importar(String ruta) {
        Class c;
        //Si no tiene paquetes no es una clase valida
        if (!ruta.contains(".")) {
            return null;
        }
        try {
            //Busca si existe la clase
            c = loader.loadClass(ruta);
        } catch (ClassNotFoundException cne) {
            return null;
        }
        Paquete paquete = new Paquete(c.getName(), c.getSimpleName(), new HashMap<>(20));
        paquete.setAlias(paquete.getIdentificador());
        //Cargamos todos los atributos public static simples
        for (Field f : c.getFields()) {
            int mods = f.getModifiers();
            if (Modifier.isStatic(mods) && Modifier.isPublic(mods)) {
                Tipo t = getTipo(f);
                if (t != null) {
                    paquete.addVariable(new EntradaVariable(f.getName(), t, true), '$');
                }
            }
        }
        //Cargamos todos las funcion formato perldoop
        for (Method m : c.getMethods()) {
            int mods = m.getModifiers();
            //Publico y estatico
            if (!Modifier.isStatic(mods) || !Modifier.isPublic(mods)) {
                break;
            }
            Class r = m.getReturnType();
            //Retornar un array de box
            if (!r.isArray() || r.getComponentType() == null || !r.getComponentType().isAssignableFrom(Box.class)) {
                break;
            }
            //Un solo argumento como array de box
            Class[] ps = m.getParameterTypes();
            if (ps.length == 1 && ps[0].isArray() || ps[0].getComponentType() != null || ps[0].getComponentType().isAssignableFrom(Box.class)) {
                EntradaFuncion funcion = new EntradaFuncion(m.getName());
                funcion.setAlias(funcion.getIdentificador());
                paquete.getFunciones().put(funcion.getIdentificador(), funcion);
            }
        }
        //Si todo ha ido bien, cargamos el paquete en el arbol
        paquetes.getPaquetes().put(c.getName(), paquete);
        return paquete;
    }

    /**
     * Transforma un tipo de dato en formato java a Perldoop
     *
     * @param f Field java
     * @return Tipo de dato Perldoop
     */
    private Tipo getTipo(Field f) {
        String nombre = f.getClass().getSimpleName();
        switch (nombre) {
            case "Boolean":
                return new Tipo(Tipo.BOOLEAN);
            case "Integer":
                return new Tipo(Tipo.INTEGER);
            case "Long":
                return new Tipo(Tipo.LONG);
            case "Float":
                return new Tipo(Tipo.FLOAT);
            case "Double":
                return new Tipo(Tipo.DOUBLE);
            case "Number":
                return new Tipo(Tipo.NUMBER);
            case "String":
                return new Tipo(Tipo.STRING);
            case "Box":
                return new Tipo(Tipo.BOX);
            case "PerlFile":
                return new Tipo(Tipo.FILE);
            default:
                return null;
        }
    }

    /**
     * Cargador de la clase que se inicializa con todas las dependencias en la variable de entorno ENV_VAR y en la
     * carpeta o jar llamados DEFAULT dentro de la ruta actual
     */
    private static class Loader extends URLClassLoader {

        public Loader() {
            super(new URL[0]);
            Set<String> libs = new HashSet<>(100);
            String env = System.getenv().get(ENV_VAR);
            if(env!=null){
                libs.addAll(Arrays.asList(env.split(";")));
            }
            libs.add(DEFAULT);
            libs.add(DEFAULT+".jar");
            for (String lib : libs) {
                try {
                    addURL(new File(lib).toURI().toURL());
                } catch (MalformedURLException ex) {
                }
            }

        }
    }

}
