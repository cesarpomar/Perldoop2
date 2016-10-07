package perldoop;

import java.util.List;
import perldoop.lib.Casting;
import perldoop.lib.Pd;
import perldoop.lib.PerlList;

/**
 *
 * @author César
 */
public class Test {

    static void pruebas() {
        Integer[] ia = new Integer[0];
        Number[] na = new Number[0];
        List<Integer> il = new PerlList<>();
        List<Number> nl = new PerlList<>();
        Casting.cast(new Casting() {
            @Override
            public Object casting(Object col) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }, ia);
    }
}
