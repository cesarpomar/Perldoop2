package perldoop.semantica.util;

import java.util.List;
import perldoop.modelo.semantica.Tipo;

/**
 * Clase para la gestion semantica de tipos
 *
 * @author César Pomar
 */
public final class Tipos {

    /**
     * Compara si dos tipos son iguales
     *
     * @param t1 Tipo 1
     * @param t2 Tipo 1
     * @return Tipo 1 igual a tipo 2
     */
    public static boolean igual(Tipo t1, Tipo t2) {
        List<Byte> tl1 = t1.getTipo();
        List<Byte> tl2 = t2.getTipo();
        if (tl1.size() != tl2.size()) {
            return false;
        } else {
            for (int i = 0; i < tl1.size(); i++) {
                if (!tl1.get(i).equals(tl2.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Compara si el tipo 1 es igual o puede convertirse al tipo 2
     *
     * @param t1 Tipo 1
     * @param t2 Tipo 2
     * @return Tipo 1 compatible con tipo 2
     */
    public static boolean compatible(Tipo t1, Tipo t2) {
        if (igual(t1, t2)) {
            return true;
        }
        List<Byte> tl1 = t1.getTipo();
        List<Byte> tl2 = t2.getTipo();
        if (tl2.size() == 1) {
            return true;
        }
        for (int i = 0; i < tl1.size(); i++) {
            switch (tl1.get(i)) {
                case Tipo.ARRAY:
                case Tipo.LIST:
                    if (tl2.get(i) != Tipo.ARRAY && tl2.get(i) != Tipo.LIST) {
                        return false;
                    }
                    break;
                default:
                    if (!tl1.get(i).equals(tl2.get(i))) {
                        return false;
                    }
            }
        }
        return true;
    }

    /**
     * Conversion del tipo a numero
     *
     * @param t Tipo
     * @return Tipo numerico
     */
    public static Tipo numero(Tipo t) {
        switch (t.getTipo().get(0)) {
            case Tipo.BOOLEAN:
                return new Tipo(Tipo.INTEGER);
            case Tipo.INTEGER:
            case Tipo.LONG:
            case Tipo.FLOAT:
            case Tipo.DOUBLE:
                return new Tipo(t);
            case Tipo.STRING:
                return new Tipo(Tipo.DOUBLE);
            case Tipo.FILE:
                return new Tipo(Tipo.INTEGER);
            case Tipo.ARRAY:
            case Tipo.LIST:
            case Tipo.MAP:
                return new Tipo(Tipo.INTEGER);
            case Tipo.REF:
                return new Tipo(Tipo.INTEGER);
        }
        return null;
    }

}