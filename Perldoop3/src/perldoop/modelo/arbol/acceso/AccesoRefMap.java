package perldoop.modelo.arbol.acceso;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -> acceso : '%' expresion
 *
 * @author César Pomar
 */
public final class AccesoRefMap extends Acceso{
    
    private Terminal porcentaje;
    private Expresion expresion;

    /**
     * Único contructor de la clase
     *
     * @param porcentaje Porcentaje
     * @param expresion Expresión
     */
    public AccesoRefMap(Terminal porcentaje, Expresion expresion) {
        setPorcentaje(porcentaje);
        setExpresion(expresion);
    }

    /**
     * Obtiene el porcentaje
     *
     * @return Porcentaje
     */
    public Terminal getPorcentaje() {
        return porcentaje;
    }

    /**
     * Establece el porcentaje
     *
     * @param porcentaje Porcentaje
     */
    public void setPorcentaje(Terminal porcentaje) {
        porcentaje.setPadre(padre);
        this.porcentaje = porcentaje;
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
        return new Simbolo[]{porcentaje, expresion};
    }
}
