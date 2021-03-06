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
     * @param tams Tamaño
     * @return Inicializacion
     */
    public static StringBuilder inicializacion(Tipo t, String... tams) {
        return inicializacion(t, false, tams);
    }

    /**
     * Crea la inicializacion de un tipo
     *
     * @param t Tipo
     * @param smart Etiqueta smart
     * @param tams Tamaño
     * @return Inicializacion
     */
    public static StringBuilder inicializacion(Tipo t, boolean smart, String... tams) {
        StringBuilder dec;
        List<Byte> subtipos = t.getTipo();
        int tam = 0;
        if (subtipos.get(0) == Tipo.ARRAY) {
            dec = new StringBuilder(100);
            dec.append("new ");
            for (int i = 0; i < subtipos.size(); i++) {
                byte st = subtipos.get(i);
                switch (st) {
                    case Tipo.ARRAY:
                        dec.append("[").append(get(tams, tam++)).append("]");
                        break;
                    case Tipo.LIST:
                        dec.insert(4, "PerlList");
                        return dec;
                    case Tipo.MAP:
                        dec.insert(4, "PerlMap");
                        return dec;
                    case Tipo.REF:
                        dec.insert(4, "Ref");
                        return dec;
                    default:
                        dec.insert(4, repr(st));
                        return dec;
                }
            }
        }
        dec = declaracion(t);
        String extendido = "";
        if (smart) {
            dec.insert(0, "new Smart");
            if (!get(tams, 0).isEmpty()) {
                extendido = subtipos.size()-1 + ",";
            } else {
                extendido = subtipos.size()-1 + "";
            }
        } else {
            dec.insert(0, "new ");
        }
        switch (t.getTipo().get(0)) {
            case Tipo.LIST:
                dec.append("(").append(extendido).append(get(tams, 0)).append(")");
                break;
            case Tipo.MAP:
                dec.append("(").append(extendido).append(get(tams, 0)).append(")");
                break;
        }
        return dec;
    }

    /**
     * Obtiene un valor siempre que la posicion exista
     *
     * @param array Array
     * @param pos posicion
     * @return Valor del array o la cadena vacia
     */
    private static String get(String[] array, int pos) {
        if (pos >= array.length || array[pos] == null) {
            return "";
        }
        return array[pos];
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
                    dec.insert(0, "PerlList<").append(">");
                    break;
                case Tipo.MAP:
                    dec.insert(0, "PerlMap<").append(">");
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
            case Tipo.NUMBER:
                return "Number";
        }
        return null;
    }

}
