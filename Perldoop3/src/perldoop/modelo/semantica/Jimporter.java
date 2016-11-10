package perldoop.modelo.semantica;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import perldoop.lib.Box;

/**
 * Clase para importar codigo java desde el classpath siempre que sea compatible con el formato perldoop
 *
 * @author César Pomar
 */
public final class Jimporter {

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
            c = Jimporter.class.getClassLoader().loadClass(ruta);
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

}
