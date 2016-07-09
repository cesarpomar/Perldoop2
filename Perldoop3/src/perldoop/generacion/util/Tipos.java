package perldoop.generacion.util;

import java.util.List;
import perldoop.modelo.semantica.Tipo;

/**
 * Clase para generacion de tipos
 *
 * @author César Pomar
 */
public final class Tipos {

    /**
     * Crea la inicializacion de un tipo
     *
     * @param t Tipo
     * @return Inicializacion
     */
    public static StringBuilder inicializacion(Tipo t) {
        return null;
    }

    /**
     * Crea la declaracion de un tipo
     *
     * @param t Tipo
     * @return Declaracion
     */
    public static StringBuilder declaracion(Tipo t) {
        StringBuilder dec = new StringBuilder(100);
        List<Byte> subtipos = t.getTipo();
        int len = t.getTipo().size();
        for (int i = len - 1; i > -1; i--) {
            byte st = subtipos.get(i);
            switch (st) {
                case Tipo.ARRAY:
                    dec.append("[]");
                    break;
                case Tipo.LIST:
                    dec.insert(0, "List<").append(">");
                    break;
                case Tipo.MAP:
                    dec.insert(0, "Map<String, ").append(">");
                    break;
                case Tipo.REF:
                    dec.insert(0, "Ref<").append(">");
                    break;
                default:
                    dec.append(repr(st));
            }
        }
        return dec;
    }

    /**
     * Crea el casting a un tipo
     *
     * @param t Tipo
     * @return Casting
     */
    public static StringBuilder casting(Tipo t) {
        return null;
    }

    /**
     * Convierte un subtipo a su nombre java
     *
     * @param subt Subtipo
     * @return Nombre
     */
    private static String repr(byte subt) {
        switch (subt) {
            case Tipo.BOOLEAN:
                return "Boolean";
            case Tipo.INTEGER:
                return "Integer";
            case Tipo.LONG:
                return "Long";
            case Tipo.FLOAT:
                return "Float";
            case Tipo.DOUBLE:
                return "Double";
            case Tipo.STRING:
                return "String";
            case Tipo.FILE:
                return "PerlFile";
            case Tipo.BOX:
                return "Box";
        }
        return null;
    }

}
