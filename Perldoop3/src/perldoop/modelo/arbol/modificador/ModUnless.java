package perldoop.modelo.arbol.modificador;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt; modificador : UNLESS expresion
 *
 * @author César Pomar
 */
public final class ModUnless extends Modificador {

    private Terminal id;
    private Expresion expresion;

    /**
     * Único contructor de la clase
     *
     * @param id For
     * @param expresion Expresión
     */
    public ModUnless(Terminal id, Expresion expresion) {
        setId(id);
        setExpresion(expresion);
    }

    /**
     * Obtiene el for
     *
     * @return For
     */
    public Terminal getId() {
        return id;
    }

    /**
     * Estabelce el for
     *
     * @param id For
     */
    public void setId(Terminal id) {
        id.setPadre(this);
        this.id = id;
    }

    /**
     * Obtiene la expresión
     *
     * @return Expresión
     */
    public Expresion getExpresion() {
        return expresion;
    }

    /**
     * Establece la expresión
     *
     * @param expresion Expresión
     */
    public void setExpresion(Expresion expresion) {
        expresion.setPadre(this);
        this.expresion = expresion;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{id, expresion};
    }

}
