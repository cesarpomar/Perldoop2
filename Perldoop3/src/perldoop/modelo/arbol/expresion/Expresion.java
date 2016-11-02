package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;

/**
 * Clase abtracta que representa todas las reduciones de expresion
 *
 * @author César Pomar
 */
public abstract class Expresion extends Simbolo{
       
    /**
     * Obtiene el simbolo que le da valor a la expresion
     * @return Simbolo valor
     */
    public abstract Simbolo getValor();
    
}
