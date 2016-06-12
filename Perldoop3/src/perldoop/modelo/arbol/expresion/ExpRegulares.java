package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.regulares.Regulares;

/**
 * Clase que representa la reduccion -> expresion : regulares
 *
 * @author César Pomar
 */
public final class ExpRegulares extends Expresion {

    private Regulares regulares;

    /**
     * Único contructor de la clase
     *
     * @param regulares Regulares
     */
    public ExpRegulares(Regulares regulares) {
        setRegulares(regulares);
    }

    /**
     * Obtiene el regulares
     *
     * @return Regulares
     */
    public Regulares getRegulares() {
        return regulares;
    }

    /**
     * Establece el regulares
     *
     * @param regulares Regulares
     */
    public void setRegulares(Regulares regulares) {
        regulares.setPadre(this);
        this.regulares = regulares;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{regulares};
    }

}
