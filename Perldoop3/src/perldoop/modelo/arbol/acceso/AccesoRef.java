package perldoop.modelo.arbol.acceso;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -> acceso : '\' expresion
 *
 * @author César Pomar
 */
public final class AccesoRef extends Acceso {

    private Terminal barra;

    /**
     * Único contructor de la clase
     *
     * @param barra Barra
     * @param expresion Expresión
     */
    public AccesoRef(Terminal barra, Expresion expresion) {
        super(expresion);
        setBarra(barra);
    }

    /**
     * Obtiene la barra
     *
     * @return Barra
     */
    public Terminal getBarra() {
        return barra;
    }

    /**
     * Establece la barra
     *
     * @param barra Barra
     */
    public void setBarra(Terminal barra) {
        barra.setPadre(padre);
        this.barra = barra;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{barra, expresion};
    }
}
