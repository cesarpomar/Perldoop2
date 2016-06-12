package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.aritmetica.Aritmetica;

/**
 * Clase que representa la reduccion -> expresion : aritmetica
 *
 * @author César Pomar
 */
public final class ExpAritmetica extends Expresion {

    private Aritmetica aritmetica;

    /**
     * Único contructor de la clase
     *
     * @param aritmetica Aritmetica
     */
    public ExpAritmetica(Aritmetica aritmetica) {
        setAritmetica(aritmetica);
    }

    /**
     * Obtiene la aritmetica
     *
     * @return Aritmetica
     */
    public Aritmetica getAritmetica() {
        return aritmetica;
    }

    /**
     * Establece laaritmetica
     *
     * @param aritmetica Aritmetica
     */
    public void setAritmetica(Aritmetica aritmetica) {
        aritmetica.setPadre(this);
        this.aritmetica = aritmetica;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{aritmetica};
    }

}
