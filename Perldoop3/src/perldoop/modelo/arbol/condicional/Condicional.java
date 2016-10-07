package perldoop.modelo.arbol.condicional;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.arbol.bloque.BloqueIf;
import perldoop.modelo.arbol.bloque.BloqueUnless;

/**
 * Clase abtracta que representa todas las reduciones de condicional
 *
 * @author César Pomar
 */
public abstract class Condicional extends Simbolo {

    /**
     * Añade el bloque else al bloque if
     *
     * @param elsif Elsif
     * @param bloqueElse Bloque Else
     * @return Elsif
     */
    public static CondicionalElsif bloqueElse(CondicionalElsif elsif, Condicional bloqueElse) {
        elsif.setBloqueElse(bloqueElse);
        return elsif;
    }

    /**
     * Añade el bloque else al bloque if o unless
     *
     * @param bloque Bloque if o unless
     * @param bloqueElse Bloque Else
     * @return Bloque
     */
    public static Bloque bloqueElse(Bloque bloque, Condicional bloqueElse) {
        if(bloque instanceof BloqueIf){
            ((BloqueIf) bloque).setBloqueElse(bloqueElse);
        }else{
            ((BloqueUnless) bloque).setBloqueElse(bloqueElse);
        }
        return bloque;
    }
}
