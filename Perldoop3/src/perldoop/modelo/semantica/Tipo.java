package perldoop.modelo.semantica;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author César
 */
public final class Tipo {

    public static final int BOOLEAN = 1;
    public static final int INTEGER = 2;
    public static final int LONG = 3;
    public static final int FLOAT = 4;
    public static final int DOUBLE = 5;
    public static final int STRING = 6;
    public static final int FILE = 7;

    public static final int ARRAY = 100;
    public static final int LIST = 101;
    public static final int MAP = 102;
    public static final int REF = 103;

    private List<Integer> tipo;

    public Tipo(int... valores) {
        tipo = new ArrayList<>(valores.length);
        for (int valor : valores) {
            add(valor);
        }
    }

    public void add(int valor) {
        tipo.add(valor);
    }

    public boolean isValido() {
        if(tipo.isEmpty()){
            return false;
        }
        int ti;
        for (int i = 0; i < tipo.size() - 1; i++) {
            ti = tipo.get(i);
            if (ti<100 || ti > 103){
                return false;
            }
        }
        ti = tipo.get(tipo.size() -1);
        return ti > 0 && ti < 8;
    }

    public boolean isSimple() {
        return tipo.size() == 1;
    }

    public static boolean compatible(Tipo t1, Tipo t2) {
        if ((t1.isSimple() && t2.isSimple()) || igual(t1, t2)) {
            return true;
        }
        return false;
    }

    public static boolean igual(Tipo t1, Tipo t2) {
        if (t1.tipo.size() != t2.tipo.size()) {
            return false;
        } else {
            for (int i = 0; i < t1.tipo.size(); i++) {
                if (!t1.tipo.get(i).equals(t2.tipo.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    public boolean compatible(Tipo t) {
        return compatible(this, t);
    }

    public boolean igual(Tipo t) {
        return igual(this, t);
    }

}
